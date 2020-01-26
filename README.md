# Simple-Mouse-Recorder

Mouse data stored in 'mouse.dat'

File is formatted to show summary of data along with the mouse path.

The format for this file is:

`[∆x:∆y:∆time(ms)][0:0:0,  ... ,∆time:∆x:∆y]`

For example:

`[-145:97:143][0:0:0, 3:-1:0, 5:-2:0, 6:-3:1, ..., 142:-145:96, 143:-145:97]`

The three number summary of the data, `[-145:97:143]` gives the change in x, y, and time between the two clicks.
