/** Disclaimer **/
    This program in itself will likely never produce a full melody which is perfect on its
    own, the purpose of this program is to give people an idea which they can use to make
    into a song
/** Disclaimer **/

This Program uses MidiSynth to produce piano notes.

This is an algorithm to pick a random chord and from this it chooses random notes to be played either in the melody portion or in the chord portion.

For the playNote and playChord methods, the first C note on the Midi Piano is at index 24 and from there on I have set a multiplier so that it picks a note and from the multiplier assigns the key used.

You can run the program from the Driver class.





From My Knowledge of Music thus far, for a song to sound appealing it must follow these rules:
    The notes should be within the same Scale
    In a chord there should be at least one space between each note
    A chord must consist of at least 2 notes to be considered a chord
    

Other Assumptions I have made
    c# is the same as d-flat, so I have made all flat notes into the sharp of the previous note



