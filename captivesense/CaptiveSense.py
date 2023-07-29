import subprocess
import os
import json

def randomize_mac_address():
    # generate a random MAC address
    mac_address = ':'.join(['{:02x}'.format(x) for x in bytearray(os.urandom(6))])

    # print old mac address
    old_mac_address = subprocess.check_output(['ifconfig', 'en0', 'ether']).decode('utf-8').split('ether ')[1]
    print('Old MAC address: ' + old_mac_address)

    subprocess.call(['sudo', 'ifconfig', 'en0', 'down'])
    subprocess.call(['sudo', 'ifconfig', 'en0', 'up'])
    # execute the shell command to change the MAC address

    print('Attempting to apply ' + mac_address)
    subprocess.call(['sudo', 'ifconfig', 'en0', 'ether', mac_address])

    # restart the network interface
    
    subprocess.call(['sudo', 'ifconfig', 'en0', 'up'])

    # print new mac address
    new_mac_address = subprocess.check_output(['ifconfig', 'en0', 'ether']).decode('utf-8').split('ether ')[1]
    print('New MAC address: ' + new_mac_address)


def authenticate():
    """
    POST /login.html HTTP/1.1
    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
    Accept-Encoding: gzip, deflate
    Accept-Language: en-US,en;q=0.9
    Cache-Control: max-age=0
    Connection: keep-alive
    Content-Length: 60
    Content-Type: application/x-www-form-urlencoded
    DNT: 1
    Host: 2.2.2.1
    Origin: http://2.2.2.1
    Referer: http://2.2.2.1/fs/customwebauth/login.html?switch_url=http://2.2.2.1/login.html&ap_mac=78:72:5d:40:22:e0&client_mac=f8:31:dc:00:86:b2&wlan=Airport-Wifi-Terminal_2&redirect=captive.apple.com/
    Upgrade-Insecure-Requests: 1
    User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36
    """

    # we want to send a POST request to the login.html page
    # we need to send the following data
import requests

def authenticate_client(url, data):
    # headers = {
    #     'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7',
    #     'Accept-Encoding': 'gzip, deflate',
    #     'Accept-Language': 'en-US,en;q=0.9',
    #     'Cache-Control': 'max-age=0',
    #     'Connection': 'keep-alive',
    #     'Content-Type': 'application/x-www-form-urlencoded',
    #     'DNT': '1',
    #     'Host': '2.2.2.1',
    #     'Origin': 'http://2.2.2.1',
    #     'Referer': 'http://2.2.2.1/fs/customwebauth/login.html?switch_url=http://2.2.2.1/login.html&redirect=captive.apple.com/',
    #     'Upgrade-Insecure-Requests': '1',
    #     'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko)',
    # }
    
    # # Convert to json object
    # url_json = json.dumps(url)
    # headers_json = json.dumps(headers)
    # data_json = json.dumps(data)
    # # combine to a single json object
    # json_object = json.dumps({"url": url_json, "headers": headers_json, "data": data_json})
    # # write to file
    # with open("request.json", "w") as outfile:
    #     outfile.write(json_object)

    # read the json file
    with open("request.json", "r") as infile:
        json_data = json.load(infile)

    # convert the json object to corresponding data types
    headers = json.loads(json_data["headers"])
    data = json.loads(json_data["data"])
    url = json.loads(json_data["url"])

    # send the request
    response = requests.post(url, headers=headers, data=data)
    print(response.text)






def main():
    
    
    # randomize_mac_address()
    
    authenticate_client(url="http://2.2.2.1", data="buttonClicked=4&redirect_url=captive.apple.com%2F&err_flag=0")
    
    # check for a captive portal




if __name__ == '__main__':
    import sys

    main()
