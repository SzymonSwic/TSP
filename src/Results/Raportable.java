package Results;

import ExperimentEnv.Population;
import RunEnv.Generation;

public interface Raportable {

    void loadToBuffer(Generation generation);

    void createResultFile();
}
