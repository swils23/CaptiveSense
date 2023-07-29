# CaptiveSense
Effectively bypass captive portals client-side via automatic authentication

The goal of CaptiveSense is to automatically detect, identify, and authenticate a client on networks that restrict access via captive portal.

### Note
Please note this project is very much a work in progress and really just a proof of concept at this point. This project exists because the guest WiFi at KFLL required clients reauthenticate every hour and I was _really_ bored waiting for my flight.

---

### TODO
- [ ] Simple PyGui
- [ ] Portal detection
- [ ] Portal identification
- [ ] Config overhaul
	- [ ] Config manager

- [X] Authenticate client with a portal


### Planned features
- Electrum desktop GUI
- Selenium-based portal detection, identification, and authentication
- Support for user-specified credentials
- Authenticate on behalf of another client (Specify alternate MAC address)
- Support for 3rd party portal templates (from file or URL)
- API
	- VPN auto-connect after portal authentication

---

<sub>:heart: - Dedicated to institutions that obtrusively "*protect*" their networks and enforce arbitrary AUPs via captive portal.</sub>
