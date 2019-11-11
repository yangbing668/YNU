from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains
from time import sleep
import os
import pandas as pd


# 设置成全局变量,防止程序意外终止
# 有一个问题是这样的,每次下载之后要将原来的选中给取消了
flag = False
if (flag):
    wb = webdriver.Chrome()
    wb.maximize_window()


def download_ei(query_way, search_word):
    # 开启浏览器并设置超时时间

    wb.implicitly_wait(10)
    wb.get('https://www.engineeringvillage.com/')

    # 输入查询关键词并设置查询方式(** 需要添加查询方式.即多选框的操作 ***)
    # select2 - sect1 - container
    if query_way != 0:
        select_query = wb.find_element_by_id('select2-sect1-container')
        select_query.click()
        sleep(2)
        select_query_by_index = wb.find_element_by_xpath('//*[@id="select2-sect1-results"]/li[{index}]'.format(index = query_way))
        select_query_by_index.click()
        sleep(2)

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
    select_box_download = wb.find_element_by_id("downloadlink")
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

    next_btn = wb.find_element_by_id("next-page-arrow")
    print("已经下载第1页数据")

    i = 1
    while (next_btn.is_enabled()):
        i = i + 1
        next_btn.click()
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
            next_btn = wb.find_element_by_id("next-page-arrow")
            print("已经下载第" + str(i) + "页数据")
        except:
            print("捕捉到异常,进行浏览器的刷新")
            wb.refresh()
            sleep(5)
            next_btn = wb.find_element_by_id("next-page-arrow")
            print("已经下载第" + str(i) + "页数据")


def merge_csvs(path, output_file_name):
    files = os.listdir(path)
    df1 = pd.read_csv(path + '/' + files[0], error_bad_lines=False)
    for file in files[1:]:
        df2 = pd.read_csv(path + '/' + file, error_bad_lines=False)
        df1 = pd.concat([df1, df2], axis=0, ignore_index=True)

    df1.to_csv(output_file_name, index=False)


if __name__ == '__main__':
    # download_ei(5, "yunnan university")
    # merge_csvs('/home/asus/Downloads/4000ByAuthor_affiliation', '4000ByAuthor_affiliation.csv')
    merge_csvs('/home/asus/Downloads/4000ByAllFields', '4000ByAllFields.csv')
