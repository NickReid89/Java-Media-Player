Hello!

Assuming JMF is installed, and a 32 bit version of Java is used, the application should compile
with the command "javac *.java". However, .MID files will not work immediately. Instead to get 
around this, you will have to delete sound.jar in:

C:\Program Files (x86)\JMF2.1.1e\lib
and in
C:\Program Files (x86)\Java\jre1.8.0_60\lib\ext

Once these two files are removed .MID files will play normally. 

A second note is with .MOV files. I have found that .MOV files are very fickle about playing.
I believe newer versions of .MOV files are not compatible with JMF. To help, I've included a
.MOV file that I have tested and seen it working. 

I have also include a .WAV files and a .MP3 file, both of which work. 

Finally, to run the application after compiling, type "java MediaPlayer" from the command
prompt and it will run.