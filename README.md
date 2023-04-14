# Kursy

### struktura
- 3-warstwowa
- Controller - przyjmowanie zapytań i wypluwanie odpowiedzi
- Service - logika biznesowa
- Repository - połączenie z bazą danych
- każde entity będzie miało swoje 3-warstwowe flow
- Nauczyciel (id, imie, nazwisko, lista jezykow)
- Kursant (id, imie, nazwisko, jezyk)
- Lekcja (id, Kursant, Nauczyciel, termin)

### funkcjonalności
- DONE - wylistowanie nauczycieli
- DONE - wylistowanie kursantów
- DONE - wylistowanie lekcji
- DONE - dodawanie nauczyciela (dodając nauczyciela, chcemy mieć możliwość wybrania kilku języków jednocześnie) - guzik na liście nauczycieli
- DONE - dodawanie kursanta (wybór nauczyciela z listy dostępnych - nie pozwalany na przypisanie nauczyciela, który nie uczy danego języka) - guzik na liście kursantów
- DONE - dodawanie lekcji (nie pozwalamy na zaplanowanie lekcji w przeszłości && nie pozwalamy na zaplanowanie lekcji w terminie, który będzie się pokrywał z inna lekcją danego nauczyciela) - guzik na liście lekcji oraz guzik przy kursancie
- DONE - usuwanie nauczyciela (soft delete) - guzik na liście nauczycieli
- DONE - suwanie kursanta (soft delete) - guzik na liście kursantów
- DONE - usuwanie lekcji (nie usuwamy lekcji, która już się zaczęła) - guzik na liście lekcji
- zmiana terminu lekcji (nie pozwalamy na przypisanie terminu, który jest niedostępny dla nauczyciela, ani nie pozwalamy na zaplanowanie jej w przeszłości) - guzik na liście lekcji
- zmiana nauczyciela dla kursanta (walidujemy/sprawdzamy język) - guzik na liście kursantów

### todo
-
