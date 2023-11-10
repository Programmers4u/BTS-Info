# BTS-Info

## Permissions
- READ_PHONE_STATE
- ACCESS_NETWORK_STATE
- ACCESS_COARSE_LOCATION

## Testing
- Xiaomi Redme5 api ver. 25

***comments***
- Depending on the device, we can read the network parameters that are implemented by the manufacturer.
- Not tested by accessing the modem directly using AT commands.

## To do
- validation and reading of network parameters (GSM, WCDMA, LTE, 5G)
- AT commands
- communication with api
- permissions for ACCESS_COARSE_LOCATION
- testing on more devices

The topic actually turned out to be interesting.
As stated by https://www2.azenqos.com/devices (generally, you should test different manufacturers and different device models yourself), the program will work well with specific devices whose manufacturers have implemented appropriate methods for reading parameters.
The test results are satisfactory because when selecting the appropriate device, you can read all the parameters currently provided by the network.

The application requires expansion and more work than I expected at the beginning, but it looks promising, it is worth developing even if the customer did not intend to buy it. Azenqos sells for USD 1,800 per license, it looks like quite a profitable business, 100 people and USD 180,000 :)

This also provides a path to a simple application with an AI detection model for detecting anamoalas in the network, e.g. a detector of wiretapping used by services to impersonate the network [https://en.wikipedia.org/wiki/IMSI-catcher] ;)

In case of problems with launching or only (text1,text2,....) is visible, you should check the permissions, in the tested Redme5 it was necessary to manually accept the Location
