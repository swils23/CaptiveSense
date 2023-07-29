import subprocess
import os
import json
import requests


def randomize_mac_address():
    # generate a random MAC address
    mac_address = ":".join(["{:02x}".format(x) for x in bytearray(os.urandom(6))])

    # print old mac address
    old_mac_address = (
        subprocess.check_output(["ifconfig", "en0", "ether"])
        .decode("utf-8")
        .split("ether ")[1]
    )
    print("Old MAC address: " + old_mac_address)

    subprocess.call(["sudo", "ifconfig", "en0", "down"])
    subprocess.call(["sudo", "ifconfig", "en0", "up"])
    # execute the shell command to change the MAC address

    print("Attempting to apply " + mac_address)
    subprocess.call(["sudo", "ifconfig", "en0", "ether", mac_address])

    # restart the network interface
    subprocess.call(["sudo", "ifconfig", "en0", "up"])

    # print new mac address
    new_mac_address = (
        subprocess.check_output(["ifconfig", "en0", "ether"])
        .decode("utf-8")
        .split("ether ")[1]
    )
    print("New MAC address: " + new_mac_address)


def authenticate_client(slug, config_file="config.json"):
    # read the config file
    with open(config_file, "r") as infile:
        config_data = json.load(infile)

    # find the config with the matching slug
    config = None
    for c in config_data:
        if c.get("slug") == slug:
            config = c
            break

    if config is not None:
        # extract the data from the config
        url = config.get("url")
        headers = config.get("headers")
        data = config.get("data")

        # send the request
        response = requests.post(url, headers=headers, data=data)
        print(response.text)
    else:
        print("Config not found for slug: " + slug)


def main():
    # randomize_mac_address()

    authenticate_client("fll-guest")

    # check for a captive portal


if __name__ == "__main__":
    import sys

    main()
