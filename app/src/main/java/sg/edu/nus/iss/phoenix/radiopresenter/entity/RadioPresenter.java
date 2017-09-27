package sg.edu.nus.iss.phoenix.radiopresenter.entity;

public class RadioPresenter {

    private String radioPresentername;
    private String radioPresenterDescription;
    private String radioPresentermailid;

    public RadioPresenter(String radioPresentername, String radioPresenterDescription, String radioPresentermailid) {
        this.radioPresentername = radioPresentername;
        this.radioPresenterDescription = radioPresenterDescription;
        this.radioPresentermailid = radioPresentermailid;
    }

    public String getRadioPresenterName() {
        return radioPresentername;
    }

    public String getRadioPresenterDescription() {
        return radioPresenterDescription;
    }

    public String getRadioPresentermailid() {
        return radioPresentermailid;
    }

    public void setRadioPresenterDescription(String radioPresenterDescription) {
        this.radioPresenterDescription = radioPresenterDescription;
    }

    public void setRadioPresentermailid(String radioPresentermailid) {
        this.radioPresentermailid = radioPresentermailid;
    }
}
