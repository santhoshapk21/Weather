package hrms.hrms.util_lib.imagechooser.listeners;


import hrms.hrms.util_lib.imagechooser.api.ChosenImage;

public interface ImageChooserListener {
    void onImageChosen(ChosenImage var1);

    void onError(String var1);
}
