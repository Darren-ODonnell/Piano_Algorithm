import javax.sound.midi.*;

public class MidiHelper {
    private final Synthesizer midiSynth;
    private final Instrument[] instr;
    private final MidiChannel[] mChannels;

    public MidiHelper() throws MidiUnavailableException {
        midiSynth = MidiSystem.getSynthesizer();
        instr = midiSynth.getDefaultSoundbank().getInstruments();
        mChannels = midiSynth.getChannels();
        midiSynth.open();
        if (instr != null && instr.length > 0) {
            midiSynth.loadInstrument(instr[0]);
        }
    }

    public void loadInstrument(int index) {
        if (instr != null && index >= 0 && index < instr.length) {
            midiSynth.loadInstrument(instr[index]);
        }
    }

    public void noteOn(int noteNumber, int velocity) {
        if (mChannels != null && mChannels.length > 0) {
            mChannels[0].noteOn(noteNumber, velocity);
        }
    }

    public void noteOff(int noteNumber) {
        if (mChannels != null && mChannels.length > 0) {
            mChannels[0].noteOff(noteNumber);
        }
    }

    public void noteOff(int noteNumber, int velocity) {
        if (mChannels != null && mChannels.length > 0) {
            mChannels[0].noteOff(noteNumber, velocity);
        }
    }

    public void close() {
        try {
            if (midiSynth != null && midiSynth.isOpen()) midiSynth.close();
        } catch (Exception ignored) {
        }
    }
}
