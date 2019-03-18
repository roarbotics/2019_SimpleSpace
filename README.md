# 2019_SimpleSpace
a simpler version of the deep space code with less functionality

If you are reading this, you are probably having problems. Good luck.

Also use [this](https://wpilib.screenstepslive.com/s/4485) (control system)
and [this](https://buildmedia.readthedocs.org/media/pdf/phoenix-documentation/latest/phoenix-documentation.pdf) (CAN stuff)

# Uploading Code
To upload code to the robot, open Visual Studio Code and open the robot project (either this one or the main one,
both should be in recents or on the desktop)

In file explorer (the top left icon), under the name of the project, find build.gradle. Right-click on this file and choose
deploy code to deploy code to the robot. Make sure the the robot is either connected by wifi (unlikely at a tournament), USB, or ethernet.

# Motor Problems
First, make sure that the motor controller is turning on when you want it too. When you run the program, the motor controllers should
stop flashing orange and turn solid, either orange, green or red. If they don't, or are flashing red, then they might not be hooked up 
to the CAN bus correctly. If they are connected, but not running, it is probably something with the code. Make sure you have the correct CAN ID
and that the program is calling them the way that you want to.

If there is something wrong with the CAN and they do not stop flashing, or you do not no the CAN ID. use the Phoenix Tuner software. This
only works when connected by USB so make sure to do that. It should connect and show you a list of all of the CAN devices on the network,
which should be the PDP, PCM, and 6 Talon SRX. Ignore the PCM and PDP, they get to do their own thing. 

If you don't know which device is which, you can click on a device and use the "Blink" button to make the lights flash on the device. You can then trace the connection from there.

If the device is there and has the correct ID but still doesn't work in the code, try running it manually from here. Select the device and go to 
the control tab, make sure that that the robot is enabled here and in the driverstation, and try running the motor to see if the lights change.

If it still doesn't work, make sure you have the right firmware and that the can connections are secure (no, duck-tape and zip-ties do not count. 
Solder and crimp only). 

If it STILL doesn't work, ¯\\_(ツ)_/¯ try getting another motor controller. You can always replace it with a PWM motor controller
just make sure to update the code (the init lines are pretty much the same just use a Spark() or Talon() or whatever, the value is the PWM address
on the side of the RIO).

# Comms Problems
Your guess is as good as mine.

Make sure that you can still get wired comms, either over ethernet or USB. If you don't, then you have bigger problems

Assuming it is the WiFi that is bork, make sure that the radio power is plugged in correctly to the 12V, 2A port on the VRM
Also make sure that the VRM is correctly wired to VRM PWR on the PDP.

If it is still not working, try and get it reflashed at the inspection place. The FTAs can probably help more than this.

If the laptop is the one having comms problems, make sure that the ethernet driver hasn't crashed. It shouldn't be an issue
anymore, but to check go to device manager and look for something with the caution sign on it, like the orange triangle. If you see
that, disable the driver and then reenable it and it should work. We also have some USB to Ethernet adapters that you could use and I think
the FTAs have them too. 
