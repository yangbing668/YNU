from selenium import webdriver
from time import sleep
import pandas as pd
import argparse
import oss2
import os
import uuid
import shutil

parser = argparse.ArgumentParser(description='EI论文爬虫')
parser.add_argument('--query_way', '-q', type=int, default=5)  # 按作者
parser.add_argument('--date', '-d', type=int, default=2020)  # 爬取年份
parser.add_argument('--search_word', '-s', default='yunnan university')
parser.add_argument('--driver_path', '-p', default='D:\\chromedriver.exe')
AccessKeyId = 'LTAI4Fs8Lq1zTsNWMCQ73LLy'
AccessKeySecret = 'Jv57YCV2sqaRSI1YaD4SWo9anj5hMb'


# 文件下载， 参数分别是查询方式、时间、查询关键字， 以及驱动的位置
# 文件下载位置自动生成, 是为了防止重名
def download_ei(query_way, date, search_word, driver_path):
    current_path = os.path.dirname(__file__)
    files_path = current_path + '/ei_files/' + str(uuid.uuid4())[:8]
    os.makedirs(files_path)
    files_path = files_path.replace('/', '\\')
    # 设置文件下载路径
    chromeOptions = webdriver.ChromeOptions()
    prefs = {"download.default_directory": files_path}
    chromeOptions.add_experimental_option("prefs", prefs)
    wb = webdriver.Chrome(driver_path, chrome_options=chromeOptions)
    wb.maximize_window()
    # 开启浏览器并设置超时时间
    # print("EI检索时间:"+str(date))
    # print("EI检索关键字:"+str(search_word))

    wb.implicitly_wait(10)
    wb.get('https://www.engineeringvillage.com/')

    # 输入查询关键词并设置查询方式(** 需要添加查询方式.即多选框的操作 ***)
    # select2 - sect1 - container
    if query_way != 0:
        select_query = wb.find_element_by_id('select2-sect1-container')
        select_query.click()
        sleep(2)
        select_query_by_index = wb.find_element_by_xpath('//*[@id="select2-sect1-results"]/li[{index}]'.format(index=query_way))
        select_query_by_index.click()
        sleep(2)

    if date != 0:
        start = date - 1883
        # print(start)
        end = 2021-date
        # print(end)
        # 选择年份
        select_date = wb.find_element_by_xpath('//*[@id="date-tab"]')
        select_date.click()
        sleep(3)
        select_down = wb.find_element_by_xpath('// *[ @ id = "select2-start-year-container"]')
        select_down.click()
        sleep(2)
        # select_date_start = wb.find_element_by_xpath('//*[@id="select2-start-year-results"]/li[3]')
        select_date_start = wb.find_element_by_xpath('//*[@id="select2-start-year-results"]/li[{index}]'.format(index=start))
        select_date_start.click()
        sleep(3)
        select_down1 = wb.find_element_by_xpath('//*[@id="select2-end-year-container"]')
        select_down1.click()
        sleep(2)
        select_date_end = wb.find_element_by_xpath('//*[@id="select2-end-year-results"]/li[{index}]'.format(index=end))
        select_date_end.click()
        sleep(3)

    element_search = wb.find_element_by_id('search-word-1')
    element_search.send_keys(search_word + '\n')
    sleep(5)

    # 对当前页面文件进行全选操作
    # 先转换为100条记录每页
    select_record_per = wb.find_element_by_id("select2-results-per-page-select-container")
    select_record_per.click()
    sleep(3)
    # #select2-results-per-page-select-result-sm82-100 这个是加密过得,所以要index来进行选择
    select_100 = wb.find_element_by_xpath('//*[@id="select2-results-per-page-select-results"]/li[3]')
    select_100.click()
    sleep(5)
    select_box = wb.find_element_by_xpath('//*[@id="select-page-col"]/div[1]/label')
    select_box.click()
    sleep(4)

    # 点击下载链接-选择下载方式-进行下载
    # select_box_download = wb.find_element_by_id("downloadlink")
    select_box_download = wb.find_element_by_xpath('//*[@id="downloadlink"]')
    select_box_download.click()
    sleep(3)
    select_box_csv = wb.find_element_by_css_selector("label[for='rdCsv']")
    select_box_csv.click()
    sleep(3)
    select_box_det = wb.find_element_by_css_selector("label[for='rdDet']")
    select_box_det.click()
    sleep(3)
    select_box_save = wb.find_element_by_id("savePrefsButton")
    select_box_save.click()
    sleep(3)

    select_box = wb.find_element_by_xpath('//*[@id="select-page-col"]/div[1]/label')
    select_box.click()
    sleep(4)
    
    print("已经下载第1页数据")
    try:
        next_btn = wb.find_element_by_id("next-page-arrow")
    except:
        print("EI数据抓取完成！")
        wb.close()
        return files_path
    # 开始进入循环体
    i = 2
    # 设置一个下载成功标志
    error_page = []
    is_donwload = False
    while (next_btn.is_enabled()):
        if (is_donwload or i==2):
            next_btn.click()
            is_donwload = False
            sleep(5)

        try:
            # 对当前页面文件进行全选操作
            select_box = wb.find_element_by_xpath('//*[@id="select-page-col"]/div[1]/label')
            select_box.click()
            sleep(5)

            # 点击下载链接-选择下载方式-进行下载
            select_box_download = wb.find_element_by_id("downloadlink")
            select_box_download.click()
            sleep(4)
            select_box_save = wb.find_element_by_id("savePrefsButton")
            select_box_save.click()
            sleep(5)
            select_box = wb.find_element_by_xpath('//*[@id="select-page-col"]/div[1]/label')
            select_box.click()
            sleep(4)
            print("已经下载第" + str(i) + "页数据")
            i = i + 1
            is_donwload = True
            try:
                next_btn = wb.find_element_by_id("next-page-arrow")
            except:
                print("EI数据抓取完成！")
                print(error_page)
                wb.close()
                break
        except:
            print("捕捉到异常,进行浏览器的刷新")
            wb.refresh()
            sleep(5)
            next_btn = wb.find_element_by_id("next-page-arrow")
            print("第" + str(i) + "页数据未能正确下载")
            error_page.append(i)
    return files_path


# EI文件合成
def merge_csvs(path, date=2013, search_word='yunnan university'):
    # print(date)
    # print(search_word)
    files = os.listdir(path)
    csv_files = [f for f in files if f.endswith(('.csv', ))]
    print(csv_files)
    df1 = pd.read_csv(path + '/' + csv_files[0], error_bad_lines=False)
    if len(csv_files) > 1:
        for file in csv_files[1:]:
            df2 = pd.read_csv(path + '/' + file, error_bad_lines=False)
            df1 = pd.concat([df1, df2], axis=0, ignore_index=True)
    # uid = uuid.uuid4()
    # output_file_name = str(uid)+'_'+str(date)+'_'+("".join(search_word.split()))+'.csv'
    output_file_name = str(date)+'_'+("".join(search_word.split()))+'.csv'
    df1.to_csv(os.path.dirname(__file__)+'/ei_compress_files/'+output_file_name, index=False)
    print('文件已经保存:' + os.path.dirname(__file__) + '/ei_compress_files/' + output_file_name)
    return os.path.dirname(__file__) + '/ei_compress_files/' + output_file_name


# 上传文件
def upload2alioss(localfile):
    # 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    auth = oss2.Auth(AccessKeyId, AccessKeySecret)
    # Endpoint以杭州为例，其它Region请按实际情况填写。
    bucket = oss2.Bucket(auth, 'http://oss-cn-shenzhen.aliyuncs.com', 'cmfighting')
    # <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
    # <yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
    filename = os.path.basename(localfile)
    bucket.put_object_from_file('ynu/eis/'+filename, localfile)
    # 显示文件访问路径
    return '文件已经上传至' + 'https://cmfighting.oss-cn-shenzhen.aliyuncs.com/ynu/eis/'+filename


# 下载文件
def download_from_alioss():
    # 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    auth = oss2.Auth(AccessKeyId, AccessKeySecret)
    # Endpoint以杭州为例，其它Region请按实际情况填写。
    bucket = oss2.Bucket(auth, 'http://oss-cn-shenzhen.aliyuncs.com', '<yourBucketName>')
    # <yourObjectName>从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
    # <yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
    bucket.get_object_to_file('<yourObjectName>', '<yourLocalFile>')


# 主执行程序
def ei_main():
    args = parser.parse_args()
    print('EI检索方式：作者单位\nEI检索时间：{date}\nEI检索关键字:{search_word}\n'.format(date=args.date, search_word=args.search_word))
#    if is_start == 'y' or is_start == 'Y':
    print("开始执行抓取程序...")
    path = download_ei(args.query_way, args.date, args.search_word, args.driver_path)
    merge_csvs(path=path, date=args.date, search_word=args.search_word)

    shutil.rmtree(path,True)

    print("删除成功")

    print("程序结束！")


if __name__ == '__main__':

    # python eiV2.py -d 2018 -search_word yunnan university
    # 主启动文件
    ei_main()

    # print('文件已经保存:' + os.path.dirname(__file__) + '/ei_compress_files/')
    # return os.pardir(__file__) + '/ei_compress_files/' + output_file_name

    # 文件上传和下载
    # re = upload2alioss('C:\\Users\\U1\\Downloads\\1115下载\\2014\\Engineering_Village_detailed_11-15-2019_251035.csv')
    # print(re)

    # csv合成程序
    # merge_csvs('C:/Users/U1/Downloads/1115/2019', date=2019)
