**Disclaimer**

    This program in itself will likely never produce a full melody which is perfect on its
    own, the purpose of this program is to give people an idea which they can use to make
    into a song

**Overview**

    This Program uses MidiSynth to produce piano notes.

    This is an algorithm to pick a random scale and from this it chooses random notes 
    to be played either in the melody portion or in the chord portion.

    For the playNote and playChord methods, the first C note on the Midi Piano is at index 24 and from there on 
    I have set a multiplier so that it picks a note and from the multiplier assigns the key used.

    You can run the program from the Driver class.


**From My Knowledge of Music thus far, for a song to sound appealing it must follow these rules:**

    The notes should be within the same scale
    In a chord there should be at least one space between each note
    A chord must consist of at least 2 notes to be considered a chord
    Chords are usually played by the left hand, so the chords are always in a lower octave to the melody
    It should follow the timings set out in music, such as quavers, crotchets, minims and semibreves
    

**Other Assumptions I have made**

    c# is the same as d-flat, so I have made all flat notes into the sharp of the previous note to align with my HashMap
    The deep and high octaves are outliers and should not be used extensively
    I use uppercase for notes within the current octave and lowercase for notes in the octave higher
        e.g. C-Scale = C, D, E, F, G, a, b, c 

**Running PianoAlgorithms Through Command Prompt**

    To Open Command Prompt, Press the start button and search for cmd
    navigate to where the project is located using **cd <folder_name>** , 
    **dir** is used to check what folders are inside current directory.
    Navigate to PianoAlgorithm\out\production\PianoAlgorithm
    then run command **java Driver** to run the program.
    