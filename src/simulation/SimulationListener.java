package simulation;

import java.util.List;

// obavestava da su se pozicije aviona promenile (za sad samo MapPanel)
public interface SimulationListener {

	void onAircraftsUpdated(List<Aircraft> aircrafts);

}
