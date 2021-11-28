# GallerySlideShow
It seems WIndows 11 removed their Slideshow feature. 
So I a JavaFX version of it.
This program will take the working directory and attempt to find all images with the file extensions of: png, jpg,jpeg, gif and bmp. If no files with those extensions are found. Than the program will close with a message saying such.
Once the images are gathered, they will be shown fullscreen. Changing every 5 seconds. Duration can be changed from the right click menu.
Left, Right arrow keys and Left Click will scroll through the images.
Spacebar will pause
Hitting Esc will close

HOW TO USE
Place the Jar file anywhere that is easy to access. (i.e., C:\Program Files\Slideshow)
Create a batch file called "start.bat", in the same directory.
Add the following line to the batch file: 

java -jar "C:\Program Files\Slideshow\GallerySlideshow.jar"

Save and Close

Next we will add a new option in the windows right click menu.
I followed the instructions from here: https://www.thewindowsclub.com/add-any-application-to-right-click-menu-in-windows-10
But basically we are creating a new registry key that points to the batch file. We can't point directly at the jar file for some window's security reason.

Open Regedit
Expand to: Computer\HKEY_CLASSES_ROOT\Directory\Background\shell
Right Click on shell
Choose New > Key
Name it "Slideshow" (Whatever you name it will be display in the menu)
Right click on the new entry "Slideshow"
Choose New > Key
Name it "command"
Click on the new entry "command"
In the Right Pane, double click (Default)
A window should popup
Change the "Value Data" to the following Line:

"C:\Program Files\Slideshow\start.bat"

Click OK

That's it!
Navigate to a folder/gallery. 
IMPORTANT: Right click anywhere on the white space (Not on a file. The registry key we made won't add our Slideshow option to the menu if you right clicked on a file)
In Windows 11, click "More Options"
Click on "Slideshow"
