import os
from time import sleep

from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.select import Select
from selenium.webdriver.support.wait import WebDriverWait
def start():

    browser.get("http://apps.webofknowledge.com/")
    browser.find_element_by_id("select2-databases-container").click()
    browser.implicitly_wait(10)
    browser.find_elements_by_class_name("select2-results__option")[1].click()
    browser.find_element_by_id("value(input1)").clear()
    browser.find_element_by_id("value(input1)").send_keys("yunnan university")
    browser.find_element_by_id("select2-select1-container").click()
    browser.find_elements_by_class_name("select2-results__option")[6].click()
    browser.find_element_by_id("settings-arrow").click()
    browser.find_element_by_id("editionitemSSCI").click()
    browser.find_element_by_id("editionitemAHCI").click()
    browser.find_element_by_id("editionitemISTP").click()
    browser.find_element_by_id("editionitemISSHP").click()
    browser.find_element_by_id("editionitemESCI").click()
    browser.find_element_by_id("editionitemCCR").click()
    browser.find_element_by_id("editionitemIC").click()
    browser.find_element_by_class_name("large-button.primary-button.margin-left-10").click()
    totalrecord=browser.find_element_by_id("footer_formatted_count").text
    totalnum=int(totalrecord.replace(",",""))
    return totalnum


def exportfile(markfrom,markto,count):

    if os.path.exists(os.getcwd() + "\\savedrecs.txt"):
        os.remove(os.getcwd() + "\\savedrecs.txt")


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
        if os.path.exists(os.getcwd()+"\\savedrecs.txt"):
            break
        sleep(1)
    #savedrecs.txt
    #写入文件
    readtext = open(os.getcwd()+"\\savedrecs.txt", "rb")
    with open(os.getcwd() + "\\data.txt", "ab+") as w:
        w.write(readtext.read())
    readtext.close()
    os.remove(os.getcwd()+"\\savedrecs.txt")

    browser.refresh()


if __name__ == '__main__':
    #browser = webdriver.Chrome()
    options = webdriver.ChromeOptions()
    prefs = {'profile.default_content_settings.popups': 0, 'download.default_directory': os.getcwd()}
    options.add_experimental_option('prefs', prefs)
    browser = webdriver.Chrome(chrome_options=options)

    totalNum=start()
    markfrom = 1
    markto = 500
    count=1
    while markto<=totalNum:
     exportfile(markfrom,markto,count)
     markfrom += 500
     if (markfrom+499)>totalNum:
         markto=markfrom+(totalNum%500-1)
     else:
        markto = markfrom + 499
     count+=1
     print(markfrom,markto)