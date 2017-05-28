drop table if exists Lekarz;
drop table if exists Wizyta;
drop table if exists Pacjent;
drop table if exists Leki;
drop table if exists Recepta;
drop table if exists Specjalizacja;
drop table if exists Specjalizacja_lekarza;
drop table if exists Skierowanie;
drop table if exists Wolne_od_pracy;
drop table if exists Platnosc;
drop table if exists Osoba;
drop table if exists Zabieg;
drop table if exists `Lista zabiegow`;
drop table if exists Zwolnienie;
drop table if exists Sala;
drop table if exists Zamowienia;
drop table if exists Produkt;
drop table if exists Konto;
drop table if exists Lekarz_Specjalizacja;
drop table if exists Recepcjonistka;
drop table if exists Entity;
drop table if exists Pielegniarka;
drop table if exists Leki_Recepta;
drop table if exists Produkt_Zamowienia;
drop table if exists Plan_dzienny;
drop table if exists Plan_pracy;
create table Lekarz (
  ID           int(10) not null auto_increment, 
  OsobaID      int(10) not null, 
  KontoID      int(11) not null, 
  Plan_pracyID int(10), 
  primary key (ID)) CHARACTER SET UTF8;
create table Wizyta (
  ID                 int(10) not null auto_increment, 
  Data               date not null, 
  Godzina            time not null, 
  Objawy             varchar(255), 
  Diagnoza           varchar(255), 
  Czy_ubezpieczony   int(1) not null, 
  Status_wizyty      varchar(20) not null, 
  `ID Specjalizacji` int(10) not null, 
  `PESEL pacjenta`   varchar(11) not null, 
  `ID lekarza`       int(10) not null, 
  RecepcjonistkaId   int(11), 
  primary key (ID)) CHARACTER SET UTF8;
create table Pacjent (
  PESEL   varchar(11) not null, 
  KontoID int(11), 
  OsobaID int(10) not null, 
  primary key (PESEL)) CHARACTER SET UTF8;
create table Leki (
  ID        int(10) not null auto_increment, 
  Nazwa     varchar(100) not null, 
  Producent varchar(100) not null, 
  Ilosc     varchar(255) not null, 
  primary key (ID)) CHARACTER SET UTF8;
create table Recepta (
  ID            int(11) not null auto_increment, 
  Data_waznosci date not null, 
  Dawkowanie    varchar(255) not null, 
  WizytaID      int(10) not null, 
  primary key (ID)) CHARACTER SET UTF8;
create table Specjalizacja (
  ID           int(10) not null auto_increment, 
  Nazwa        varchar(100) not null, 
  Czas_wizyty  int(10) not null, 
  Koszt_wizyty decimal(10, 2) not null, 
  primary key (ID)) CHARACTER SET UTF8;
create table Specjalizacja_lekarza (
  `ID lekarza`       int(10) not null, 
  `ID Specjalizacji` int(10) not null, 
  primary key (`ID lekarza`, 
  `ID Specjalizacji`)) CHARACTER SET UTF8;
create table `Godziny pracy` (
  `ID godzin`  int(10) not null auto_increment, 
  Od           int(10), 
  Do           int(10), 
  `Przerwa od` int(10), 
  `Przerwa do` int(10), 
  primary key (`ID godzin`)) CHARACTER SET UTF8;
create table Skierowanie (
  ID             int(10) not null auto_increment, 
  `ID wizyty`    int(10) not null, 
  Skierowanie_do varchar(50) not null, 
  Rozpoznanie    varchar(255) not null, 
  Skierowanie_na varchar(100) not null, 
  primary key (ID)) CHARACTER SET UTF8;
create table Wolne_od_pracy (
  `ID wolnego`   int(11) not null auto_increment, 
  `Plan pracyID` int(10) not null, 
  Powod          varchar(100) not null, 
  Od_dnia        date not null, 
  Od_godziny     time not null, 
  Do_dnia        date not null, 
  Do_godziny     time not null, 
  primary key (`ID wolnego`)) CHARACTER SET UTF8;
create table Platnosc (
  ID               int(11) not null auto_increment, 
  Kwota            decimal(10, 2) not null, 
  Data_platnosci   date, 
  Tytul_platnosci  varchar(255) not null, 
  `ID wizyty`      int(10) not null, 
  RecepcjonistkaId int(11) not null, 
  primary key (ID)) CHARACTER SET UTF8;
create table Osoba (
  ID             int(10) not null auto_increment, 
  Imie           varchar(50) not null, 
  Nazwisko       varchar(255) not null, 
  Miejscowosc    varchar(50) not null, 
  Numer_domu     int(10) not null, 
  Ulica          varchar(100), 
  Kod_pocztowy   varchar(6) not null, 
  Poczta         varchar(50) not null, 
  Numer_telefonu varchar(12) not null, 
  primary key (ID)) CHARACTER SET UTF8;
create table Zabieg (
  ID                 int(10) not null auto_increment, 
  `Lista zabiegowID` int(10) not null, 
  PielegniarkaID     int(10) not null, 
  SkierowanieID      int(10), 
  Data_wykonania     date, 
  Uwagi              varchar(255), 
  primary key (ID)) CHARACTER SET UTF8;
create table `Lista zabiegow` (
  ID    int(10) not null auto_increment, 
  Nazwa varchar(255) not null, 
  Cena  decimal(10, 2), 
  primary key (ID)) CHARACTER SET UTF8;
create table Zwolnienie (
  ID               int(10) not null auto_increment, 
  WizytaID         int(10) not null, 
  Zwolnienie_do    date not null, 
  Powod_zwolnienia varchar(255) not null, 
  primary key (ID)) CHARACTER SET UTF8;
create table Sala (
  Numer_sali int(10) not null auto_increment, 
  Sala_dla   varchar(100), 
  primary key (Numer_sali)) CHARACTER SET UTF8;
create table Zamowienia (
  ID              int(10) not null auto_increment, 
  Data_zamowienia date not null, 
  Godzina         time not null, 
  PielegniarkaID  int(10) not null, 
  SalaNr_sali     int(10) not null, 
  primary key (ID)) CHARACTER SET UTF8;
create table Produkt (
  ID    int(10) not null auto_increment, 
  Nazwa varchar(255) not null, 
  Cena  decimal(10, 2) not null, 
  primary key (ID)) CHARACTER SET UTF8;
create table Konto (
  ID        int(11) not null auto_increment, 
  Login     varchar(50) not null unique, 
  Haslo     varchar(50) not null, 
  Typ_konta int(11) not null, 
  primary key (ID)) CHARACTER SET UTF8;
create table Lekarz_Specjalizacja (
  LekarzID        int(10) not null, 
  SpecjalizacjaID int(10) not null, 
  primary key (LekarzID, 
  SpecjalizacjaID)) CHARACTER SET UTF8;
create table Recepcjonistka (
  Id           int(11) not null auto_increment, 
  KontoID      int(11) not null, 
  OsobaID      int(10) not null, 
  Plan_pracyID int(10), 
  primary key (Id)) CHARACTER SET UTF8;
create table Entity (
  ID               int(11) not null auto_increment, 
  RecepcjonistkaId int(11) not null, 
  Attribute        int(10), 
  primary key (ID)) CHARACTER SET UTF8;
create table Pielegniarka (
  ID           int(10) not null auto_increment, 
  AdresID      int(10) not null, 
  KontoID      int(11) not null, 
  Plan_pracyID int(10), 
  primary key (ID)) CHARACTER SET UTF8;
create table Leki_Recepta (
  ID                 int(11) not null auto_increment, 
  LekiID             int(10) not null, 
  ReceptaID          int(11) not null, 
  Dawkowanie         varchar(255) not null, 
  Procent_refundacji int(10) not null, 
  primary key (ID)) CHARACTER SET UTF8;
create table Produkt_Zamowienia (
  ProduktID    int(10) not null, 
  ZamowieniaID int(10) not null, 
  Ilosc        int(10), 
  primary key (ProduktID, 
  ZamowieniaID)) CHARACTER SET UTF8;
create table Plan_dzienny (
  ID             int(11) not null auto_increment, 
  Dzien          int(7) not null, 
  Poczatek       time not null, 
  Koniec         time not null, 
  Przerwa_od     time not null, 
  Przerwa_do     time not null, 
  SalaNr_sali    int(10) not null, 
  Plan_pracyID   int(10) not null, 
  SalaNumer_sali int(10) not null, 
  primary key (ID)) CHARACTER SET UTF8;
create table Plan_pracy (
  ID       int(10) not null auto_increment, 
  Umowa_od date not null, 
  Wyplata  decimal(6, 2) not null, 
  primary key (ID)) CHARACTER SET UTF8;
alter table Wizyta add index FKWizyta305603 (`ID lekarza`), add constraint FKWizyta305603 foreign key (`ID lekarza`) references Lekarz (ID);
alter table Wizyta add index FKWizyta562970 (`PESEL pacjenta`), add constraint FKWizyta562970 foreign key (`PESEL pacjenta`) references Pacjent (PESEL);
alter table Specjalizacja_lekarza add index FKSpecjaliza391747 (`ID lekarza`), add constraint FKSpecjaliza391747 foreign key (`ID lekarza`) references Lekarz (ID);
alter table Specjalizacja_lekarza add index FKSpecjaliza375972 (`ID Specjalizacji`), add constraint FKSpecjaliza375972 foreign key (`ID Specjalizacji`) references Specjalizacja (ID);
alter table Skierowanie add index FKSkierowani15044 (`ID wizyty`), add constraint FKSkierowani15044 foreign key (`ID wizyty`) references Wizyta (ID);
alter table Wizyta add index FKWizyta321378 (`ID Specjalizacji`), add constraint FKWizyta321378 foreign key (`ID Specjalizacji`) references Specjalizacja (ID);
alter table Platnosc add index FKPlatnosc832142 (`ID wizyty`), add constraint FKPlatnosc832142 foreign key (`ID wizyty`) references Wizyta (ID);
alter table Zabieg add index FKZabieg409057 (`Lista zabiegowID`), add constraint FKZabieg409057 foreign key (`Lista zabiegowID`) references `Lista zabiegow` (ID);
alter table Zabieg add index FKZabieg629994 (SkierowanieID), add constraint FKZabieg629994 foreign key (SkierowanieID) references Skierowanie (ID);
alter table Zwolnienie add index FKZwolnienie600764 (WizytaID), add constraint FKZwolnienie600764 foreign key (WizytaID) references Wizyta (ID);
alter table Lekarz add index FKLekarz871848 (OsobaID), add constraint FKLekarz871848 foreign key (OsobaID) references Osoba (ID);
alter table Lekarz_Specjalizacja add index FKLekarz_Spe632245 (LekarzID), add constraint FKLekarz_Spe632245 foreign key (LekarzID) references Lekarz (ID);
alter table Lekarz_Specjalizacja add index FKLekarz_Spe841770 (SpecjalizacjaID), add constraint FKLekarz_Spe841770 foreign key (SpecjalizacjaID) references Specjalizacja (ID);
alter table Lekarz add index FKLekarz622720 (KontoID), add constraint FKLekarz622720 foreign key (KontoID) references Konto (ID);
alter table Recepcjonistka add index FKRecepcjoni401855 (KontoID), add constraint FKRecepcjoni401855 foreign key (KontoID) references Konto (ID);
alter table Zamowienia add index FKZamowienia308433 (SalaNr_sali), add constraint FKZamowienia308433 foreign key (SalaNr_sali) references Sala (Numer_sali);
alter table Entity add index FKEntity415445 (RecepcjonistkaId), add constraint FKEntity415445 foreign key (RecepcjonistkaId) references Recepcjonistka (Id);
alter table Wizyta add index FKWizyta317167 (RecepcjonistkaId), add constraint FKWizyta317167 foreign key (RecepcjonistkaId) references Recepcjonistka (Id);
alter table Pacjent add index FKPacjent536955 (KontoID), add constraint FKPacjent536955 foreign key (KontoID) references Konto (ID);
alter table Zabieg add index FKZabieg233534 (PielegniarkaID), add constraint FKZabieg233534 foreign key (PielegniarkaID) references Pielegniarka (ID);
alter table Zamowienia add index FKZamowienia352162 (PielegniarkaID), add constraint FKZamowienia352162 foreign key (PielegniarkaID) references Pielegniarka (ID);
alter table Pielegniarka add index FKPielegniar225150 (AdresID), add constraint FKPielegniar225150 foreign key (AdresID) references Osoba (ID);
alter table Pielegniarka add index FKPielegniar266378 (KontoID), add constraint FKPielegniar266378 foreign key (KontoID) references Konto (ID);
alter table Leki_Recepta add index FKLeki_Recep386629 (LekiID), add constraint FKLeki_Recep386629 foreign key (LekiID) references Leki (ID);
alter table Recepta add index FKRecepta782813 (WizytaID), add constraint FKRecepta782813 foreign key (WizytaID) references Wizyta (ID);
alter table Leki_Recepta add index FKLeki_Recep676042 (ReceptaID), add constraint FKLeki_Recep676042 foreign key (ReceptaID) references Recepta (ID);
alter table Recepcjonistka add index FKRecepcjoni152727 (OsobaID), add constraint FKRecepcjoni152727 foreign key (OsobaID) references Osoba (ID);
alter table Pacjent add index FKPacjent287827 (OsobaID), add constraint FKPacjent287827 foreign key (OsobaID) references Osoba (ID);
alter table Produkt_Zamowienia add index FKProdukt_Za756293 (ProduktID), add constraint FKProdukt_Za756293 foreign key (ProduktID) references Produkt (ID);
alter table Produkt_Zamowienia add index FKProdukt_Za640749 (ZamowieniaID), add constraint FKProdukt_Za640749 foreign key (ZamowieniaID) references Zamowienia (ID);
alter table Platnosc add index FKPlatnosc422693 (RecepcjonistkaId), add constraint FKPlatnosc422693 foreign key (RecepcjonistkaId) references Recepcjonistka (Id);
alter table Wolne_od_pracy add index FKWolne_od_p571826 (`Plan pracyID`), add constraint FKWolne_od_p571826 foreign key (`Plan pracyID`) references Plan_pracy (ID);
alter table Lekarz add index FKLekarz200414 (Plan_pracyID), add constraint FKLekarz200414 foreign key (Plan_pracyID) references Plan_pracy (ID);
alter table Pielegniarka add index FKPielegniar556756 (Plan_pracyID), add constraint FKPielegniar556756 foreign key (Plan_pracyID) references Plan_pracy (ID);
alter table Recepcjonistka add index FKRecepcjoni746600 (Plan_pracyID), add constraint FKRecepcjoni746600 foreign key (Plan_pracyID) references Plan_pracy (ID);
alter table Plan_dzienny add index FKPlan_dzien976928 (Plan_pracyID), add constraint FKPlan_dzien976928 foreign key (Plan_pracyID) references Plan_pracy (ID);
alter table Plan_dzienny add index FKPlan_dzien61366 (SalaNumer_sali), add constraint FKPlan_dzien61366 foreign key (SalaNumer_sali) references Sala (Numer_sali);
