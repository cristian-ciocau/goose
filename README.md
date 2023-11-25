# Goose
Goose is your favourite RIO delivering flight data to your Electronic Flight Bag.

It does receive GNSS data from [gpsd](https://gpsd.gitlab.io/gpsd/index.html) and ADS-B
traffic information from [Dump1090](https://github.com/antirez/dump1090).

The current implementation supports [ForeFlight](https://foreflight.com/), by implementing the [GDL90 Protocol](https://www.faa.gov/sites/faa.gov/files/air_traffic/technology/adsb/archival/GDL90_Public_ICD_RevA.PDF) and ForeFlight's [own](https://www.foreflight.com/connect/spec/) specifications. 

### Disclaimer
Pilots should consider the GNSS and EFB limitations and should follow the approved material and procedures
mandated by the Aviation Authority.