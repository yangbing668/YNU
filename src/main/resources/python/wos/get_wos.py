import csv
import os
import sys
from time import sleep

from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.select import Select
from selenium.webdriver.support.wait import WebDriverWait
def start(fromTime,toTime,art_type,num):

    browser.get("http://apps.webofknowledge.com/")
    browser.find_element_by_id("select2-databases-container").click()
    browser.implicitly_wait(10)
    browser.find_elements_by_class_name("select2-results__option")[1].click()
    browser.find_element_by_id("value(input1)").clear()
    browser.find_element_by_id("value(input1)").send_keys("yunnan university")
    browser.find_element_by_id("select2-select1-container").click()
    browser.find_elements_by_class_name("select2-results__option")[6].click()
    browser.find_element_by_xpath("//*[@id=\"timespan\"]/div[2]/div/span/span[1]/span/span[2]").click()
    browser.find_elements_by_class_name("select2-results__option")[6].click()
    browser.find_element_by_xpath("//*[@id=\"timespan\"]/div[3]/div/span[2]/span[1]/span/span[2]").click()
    browser.find_element_by_xpath("/html/body/span[37]/span/span[1]/input").send_keys(fromTime)
    browser.find_element_by_xpath("/html/body/span[37]/span/span[1]/input").send_keys(Keys.ENTER)
    browser.find_element_by_xpath("//*[@id=\"timespan\"]/div[3]/div/span[4]/span[1]/span/span[2]").click()
    browser.find_element_by_xpath("/html/body/span[37]/span/span[1]/input").send_keys(toTime)
    browser.find_element_by_xpath("/html/body/span[37]/span/span[1]/input").send_keys(Keys.ENTER)
   # browser.find_element_by_id("settings-arrow").click()
    print(num)
    if num==1:

        browser.find_element_by_xpath("//*[@id=\"WOS_GeneralSearch_input_form\"]/div[2]/div[2]/span").click()

        if art_type=='SCI':
            browser.find_element_by_id("editionitemISTP").click()
        elif art_type=='CPCI-S':
            browser.find_element_by_id("editionitemSCI").click()

        browser.find_element_by_id("editionitemSSCI").click()
        browser.find_element_by_id("editionitemAHCI").click()
        browser.find_element_by_id("editionitemISSHP").click()
        browser.find_element_by_id("editionitemESCI").click()
        browser.find_element_by_id("editionitemCCR").click()
        browser.find_element_by_id("editionitemIC").click()
    browser.find_element_by_class_name("large-button.primary-button.margin-left-10").click()
    totalrecord=browser.find_element_by_id("footer_formatted_count").text
    totalnum=int(totalrecord.replace(",",""))
    return totalnum


def exportfile(markfrom,markto,count,fromyear,art_type,path):
    print(path+"\\savedrecs.txt")
    if os.path.exists(path + "\\savedrecs.txt"):
        os.remove(path + "\\savedrecs.txt")


    browser.find_element_by_id("exportTypeName").click()
    if count==1:
        browser.find_element_by_class_name("quickOutputOther.subnav-link").click()
    browser.find_element_by_id("numberOfRecordsRange").click()
    browser.find_element_by_id("markFrom").clear()
    browser.find_element_by_id("markFrom").send_keys(markfrom)
    browser.find_element_by_id("markTo").clear()
    browser.find_element_by_id("markTo").send_keys(markto)
    browser.find_element_by_id("select2-bib_fields-container").click()
    browser.find_elements_by_class_name("select2-results__option")[2].click()
    browser.find_element_by_id("select2-saveOptions-container").click()
    browser.find_elements_by_class_name("select2-results__option")[6].click()
    browser.find_element_by_id("exportButton").click()
    browser.find_element_by_link_text("关闭").click()
    #等待文件下载完成
    while True:
        if os.path.exists(path+"\\savedrecs.txt"):
            break
        sleep(1)
    #savedrecs.txt
    #写入文件
    readtext = open(path+"\\savedrecs.txt", "rb")
    if not os.path.exists(path+"\\"+art_type):
        os.makedirs(path+"\\"+art_type)

    with open(path + "\\"+art_type+"\\"+str(fromyear)+".txt", "ab+") as w:
        w.write(readtext.read())
    readtext.close()
    os.remove(path+"\\savedrecs.txt")
    sleep(3)
    browser.refresh()


def getwos(fromyear,toyear,art_type,path):



    #fromyear=2017
    #toyear=2019
    #art_type='SCI'

    num=1

    count = 1
    while fromyear<=toyear:

        totalNum=start(fromyear,fromyear,art_type,num)

        markfrom = 1
        markto = 500
        if totalNum < markto:
            exportfile(markfrom,totalNum,count,fromyear,art_type,path)
            print(markfrom,totalNum)
            count+=1
        while markto<=totalNum:
            exportfile(markfrom,markto,count,fromyear,art_type,path)
            markfrom += 500
            if (markfrom+499)>totalNum:
                markto=markfrom+(totalNum%500-1)
            else: markto = markfrom + 499
            count+=1
            print(markfrom,markto)

        fromyear+=1
        num+=1
    browser.close()


if __name__ == '__main__':
    path = sys.argv[4]
    if os.path.exists(path + "\\data.txt"):
        os.remove(path + "\\data.txt")

    #browser = webdriver.Chrome()
    options = webdriver.ChromeOptions()
    prefs = {'profile.default_content_settings.popups': 0, 'download.default_directory': path}
    options.add_experimental_option('prefs', prefs)
    browser = webdriver.Chrome(chrome_options=options)
    #传入参数
    #print(path)
    getwos(int(sys.argv[1]),int(sys.argv[2]),sys.argv[3],path)