# BTS-Info

Uprawnienia

- READ_PHONE_STATE
- ACCESS_NETWORK_STATE
- ACCESS_COARSE_LOCATION

Testowane
- Xiaomi Redme5 api ver. 25

W zależności od urządzenia możemy odczytać parametry sieci które są zaimplementowane przez producent.
Nie testowane przez dostęp bezpośrednio do modemu za pomocą komend komendy AT.

Do zrobienia
- walidacja i odczyt parametrów sieci (GSM, WCDMA, LTE, 5G)
- komendy AT
- komunikacja z api
- uprawnienia do ACCESS_COARSE_LOCATION
- przetestowanie na większej ilości urządzeń

Temat w sumie okazał się nawet ciekawy.
Tak jak podaje https://www2.azenqos.com/devices (generalnie należałoby samemu przetestować różnych producentów i różne modele urządzeń), program będzie działał dobrze z konkretnymi urządzeniami których producenci zaimplementowali odpowiednie metody do odczytu parametrów.
Wyniki testu są zadawalające, bo przy wyborze odpowiedniego urządzenia można odczytać wszytskie parametry które aktualnie udostępnia sieć.

Aplikacja wymaga rozbudowy i większego nakładu czasu pracy niż zakładałem na początku ale wygląda obiecująco, warto ją rozwijać nawet gdyby klient nie zamierzał tego kupić. Azenqos sprzedaje po 1800 usd za licencję, to wygląda na całkiem dochodowy biznes, 100 sosób i 180 000 usd :)

Daje to też drogę do prostej aplikacji z modelem detekcji SI do wykrywania anamoali w sieci, np. wykrywacz podsłuchów których używają służby do podszywania się pod sieć [https://en.wikipedia.org/wiki/IMSI-catcher] ;)

W razie problemów z uruchomieniem lub widać tylko (text1,text2,....), należy sprawdzić uprawnienia, w testowanym Redme5 konieczne było ręczne zaakceptowanie Lokalizacji (Location)

