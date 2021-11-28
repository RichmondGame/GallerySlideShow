# GallerySlideShow
It seems WIndows 11 removed their Slideshow feature. <br>
So I a JavaFX version of it.<br>
This program will take the working directory and attempt to find all images with the file extensions of: png, jpg,jpeg, gif and bmp. If no files with those extensions are found. Than the program will close with a message saying such.<br>
Once the images are gathered, they will be shown fullscreen. Changing every 5 seconds. Duration can be changed from the right click menu.<br>
Left, Right arrow keys and Left Click will scroll through the images.<br>
Spacebar will pause<br>
Hitting Esc will close<br>

HOW TO USE<br>
Place the Jar file anywhere that is easy to access. (i.e., C:\Program Files\Slideshow)<br>
Create a batch file called "start.bat", in the same directory.<br>
Add the following line to the batch file: <br>

java -jar "C:\Program Files\Slideshow\GallerySlideshow.jar"<br>

Save and Close<br>

Next we will add a new option in the windows right click menu.<br>
I followed the instructions from here: https://www.thewindowsclub.com/add-any-application-to-right-click-menu-in-windows-10<br>
But basically we are creating a new registry key that points to the batch file. We can't point directly at the jar file for some window's security reason.<br>

Open Regedit<br>
Expand to: Computer\HKEY_CLASSES_ROOT\Directory\Background\shell<br>
Right Click on shell<br>
Choose New > Key<br>
Name it "Slideshow" (Whatever you name it will be display in the menu)<br>
Right click on the new entry "Slideshow"<br>
Choose New > Key<br>
Name it "command"<br>
Click on the new entry "command"<br>
In the Right Pane, double click (Default)<br>
A window should popup<br>
Change the "Value Data" to the following Line:<br>

"C:\Program Files\Slideshow\start.bat"<br>

Click OK<br>

That's it!<br>
Navigate to a folder/gallery. <br>
IMPORTANT: Right click anywhere on the white space (Not on a file. The registry key we made won't add our Slideshow option to the menu if you right clicked on a file)<br>
In Windows 11, click "More Options"<br>
Click on "Slideshow"<br>
