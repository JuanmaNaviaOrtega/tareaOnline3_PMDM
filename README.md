****Ejercicio1****<br>
-
-Esta es una aplicación simple para convertir entre euros y dólares que permite al usuario configurar el tipo de cambio y personalizar el color de fondo de la aplicación,  utiliza DataStore para guardar y recuperar las preferencias del usuario de manera eficiente. <br>

-----Funcionamiento-----<br>
###Conversion de Monedas###:<br>
-El usuario introduce una cantidad en el campo de texto.<br>
-Selecciona la dirección de la conversión (de euros a dólares o de dólares a euros).<br>
-La aplicacion calcula y muestra el resultado<br>

###Configuración de Preferencias###<br>
-Se puede acceder a la pantalla de preferencias para:<br>
Cambiar el color de fondo de la aplicación (azul, verde o amarillo).<br>
Establecer el tipo de cambio (ratio de conversión entre euros y dólares).<br>
- Las preferencias del usuario (color de fondo y tipo de cambio) se guardan utilizando DataStore y se cargan al iniciar la app<br>

 ###Menu Opciones###<br>
-Se puede acceder a preferencias o Arcerca de (donde muestro informacion sobre el desarrollador, es decir sobre mi).<br>

  ------Cómo Usar-----<br>
  ###Convresion de monedas###:<br>
 -Primero introduce una cantidad en el campo de texto Luego selecciona la dirección de la conversión (euros a dólares o dólares a euros) y por ultimo el resultado se mostrará automáticamente.<br>

###cambiar Preferencias###:<br>
-Primero haz clic en el menú de opciones (tres puntos en la esquina superior derecha). Selecciona Preferencias y Cambia el color de fondo o el tipo de cambio por ultimo guarda los cambios.<br>

###Acerca de ###<br>
-Haz clic en el menú de opciones y selecciona Acerca de y Verás información sobre la aplicación y el desarrollador(sobre mi).<br>

![2](https://github.com/user-attachments/assets/1e1ccd31-8f47-4346-bbc9-2a2f7c95255c)


****Ejercicio2****<br>
-
- Esta es una aplicación para gestionar y programar alarmas.<br>
- Permite al usuario agregar alarmas con una duración, descripción y sonido específicos.<br>
- Las alarmas se ejecutan en segundo plano utilizando WorkManager, y cuando suenan, se muestra una notificación y se reproduce un sonido.<br>
  
-----Funcionamiento-----<br>
###Agregar Alarmas###:<br>
-Las alarmas se almacenan en un archivo de texto (alarmas.txt) en el almacenamiento externo de la aplicación.<br>
-Cada alarma tiene una duración (en minutos), una descripción y un sonido asociado.<br>

###Programar Alarmas###:<br>
-Cuando una alarma suena, se muestra una notificación y se reproduce un sonido.<br>

###Interfaz de Usuario###:<br>
-La pantalla principal muestra el tiempo restante total y el número de alarmas pendientes.<br>
-El usuario puede iniciar, reiniciar o editar las alarmas desde la interfaz.<br>

###Notificaciones y Sonidos###:<br>
-Cuando una alarma se completa, se muestra una notificación y un mensaje emergente  con la descripción de la alarma.<br>

***Paco hay una cosa a tener en cuenta***<br>
-Cuando una alarma suena y se muestra el mensaje emergente, la interfaz de usuario(Tiempo restante y alarmas) no se actualiza automaticamente correctamente .
 Para ver los cambios, se debe salir al menú y volver a la aplicación.<br>
-El archivo con el sonido se llama pigle, así que cuando vayas a agregar una alarma en el apartado sonido pon pigle<br>
-


------Cómo Usar-----<br>

###Agregar Alarmas###:<br>
-Ve a la pantalla de edición de alarmas y agrega nuevas alarmas con una duración, descripción y sonido.<br>

###Iniciar Alarmas###:<br>
-En la pantalla principal, haz clic en "Iniciar Alarmas" para programar las alarmas.<br>

###Reiniciar Alarmas###:<br>
-Haz clic en "Reiniciar Alarmas" para eliminar todas las alarmas y comenzar de nuevo.<br>

###Ver Alarmas Pendientes##:<br>
-La pantalla principal muestra el tiempo restante total y el número de alarmas pendientes.<br>

![3](https://github.com/user-attachments/assets/7a9dd0d2-c7bc-4de7-ae22-8e78606fec85)

****Ejercicio3**** <br>
- 
- Esta es una aplicación Android simple para gestionar una lista de libros que permite agregar, editar, eliminar y ver detalles de libros, los datos se almacenan en una base de datos local SQLite<br>

---Funcionalidades---<br>
###Ver lista de libros###<br>
- Al iniciar la aplicación, se muestra dos libros que he puesto como predeterminados si la base de datos está vacia, así evito posibles duplicaciones a la hora de volver iniciar la app.<br>
- Cada libro muestra su título, autor, género, fecha de publicación y una imagen.<br>

###Agreagar un nuevo Libro###<br>
- Primero toca el boton flotante (➕) en la esquina inferior derecha.<br>
- Luego completa el formulario con los detalles del libro:<br>
● Título: Nombre del libro.<br>
● Autor: Nombre del autor.<br>
● Enlace: URL de referencia (por ejemplo, un enlace a Wikipedia).<br>
● Fecha de Publicación: Fecha en la que se publicó el libro.<br>
● Género: Género literario (por ejemplo, Fantasía, Realismo mágico).<br>
● Foto: URL de una imagen del libro.<br>
- Toca "Guardar" para agregar el libro a la lista.<br>

  ###Editar un libro Existente###:<br>
  -Primero mantén pulsado un libro de la lista y se abrira el mismo formulario con los datos actuales del libro, realiza los cambios necesarios y pulsa en guardar<br>

###Eliminar un libro###:<br>
- Desliza un libro hacia la derecha para eliminarlo.<br>

###Ver detalles del libro###:<br>
-Toca un libro en la lista para abrir su enlace en un navegador web.<br>


  ![33](https://github.com/user-attachments/assets/806e4d84-3476-40a4-abd6-80a245adee7c)

  
