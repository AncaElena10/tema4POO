MOISA ANCA-ELENA
321 CA
TEMA4 POO - MOCKITO LITE

Pentru rezolvarea temei am folosit o clasa interna in interiorul clasei Mockito.
Aceasta stocheaza valorile de return, exceptiile pe care utilizatorul clasei
Mockito le doreste pentru metoda execute(...)
In constructorul clasei MockableBlackMagic am initializat variabilele de care 
voi avea nevoie: isWatching (pentru verificare), mockedObject, mReturnabless 
(valorile de return sau exceptiile pentru metodele execute), currentInputType si 
un obiect de tip Unit.

-> In metoda execute() prin currentInputType = unit am setat ultima valoare de input
cu unit pentru a putea determina cand se apeleaza metoda execute(). In cazul in 
care nu este nicio valoare stocata, se returneaza null, altfel se arunca o 
exceptie. Prin returnabless.poll() - se scoate elementul returnat/aruncat; la 
urmatorul apel se returneaza urmatorul element, sau null.

-> In metoda execute(A arg) setez utima valoare care s-a furnizat 
(currentInputType = arg). Daca nu exista nicio valoare stocata se returneaza 
null altfel se arunca exceptie, stergand elementul din colectie si se trece la 
urmatoarele valori. Returnez valoarea stocata prin metoda 
cacheReturnTypeForLastInputType(B value).

-> In metoda cacheReturnTypeForLastInputType(B value) stochez valoarea pe care doresc
sa o returnez cand apelez thenReturn(Object string) intr-o lista.

-> In metoda cacheThrowTypeForLastInputType(Exception exc) stochez intr-o lista 
exceptia pe care o voi returna la apelul functiei thenThrow(Exception exc).
Am folosit o stiva pentru a stoca obiectele reale si pentru a putea obtine mai
usor ultimul obiect inserat in stiva.

-> In metoda mock creez o 'imagine' a obiectului real prin intermediul careia pot
face apelul la metoda execute(). O inserez in stiva si o returnez.

-> In functia watch() - Mockito se 'uita' la ultimul obiect adaugat si returneaza 
instanta.

-> In functia when doar returnez instanta.

-> In functiile thenReturn(Object string) si thenThrow(Exception exc) returnez 
ultimele valori stocate si returnez instanta.

-> In functia andBeDoneWithIt() gasesc ultimul obiect mock-uit si opresc.. 
'watching-ul' :D

Functiile atLeastOnce, exacltyOnce, times returneaza 0 pentru ca nu sunt
implementate :")