package fr.wildcodeschool.florianscoffee;


import android.os.Parcel;
import android.os.Parcelable;

class CoffeeShopModel implements Parcelable {

    private String contact;
    private String description;
    private String emplacement;
    private String emplacementSuiv;
    private String horaireClose;
    private String horaireCloseSuiv;
    private String horaireOpen;
    private String horaireOpenSuiv;
    private Double latitude;
    private Double latitudeSuiv;
    private Double longitude;
    private Double longitudeSuiv;
    private String nom;
    private String photoUrl;

    public CoffeeShopModel() {
    }

    public CoffeeShopModel(String contact, String description, String emplacement,
                           String emplacementSuiv, String horaireClose, String horaireCloseSuiv,
                           String horaireOpen, String horaireOpenSuiv,
                           Double latitude, Double latitudeSuiv, Double longitude, Double longitudeSuiv,
                           String nom, String photoUrl) {
        this.contact = contact;
        this.description = description;
        this.emplacement = emplacement;
        this.emplacementSuiv = emplacementSuiv;
        this.horaireClose = horaireClose;
        this.horaireCloseSuiv = horaireCloseSuiv;
        this.horaireOpen = horaireOpen;
        this.horaireOpenSuiv = horaireOpenSuiv;
        this.latitude = latitude;
        this.latitudeSuiv = latitudeSuiv;
        this.longitude = longitude;
        this.longitudeSuiv = longitudeSuiv;
        this.nom = nom;
        this.photoUrl = photoUrl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getEmplacementSuiv() {
        return emplacementSuiv;
    }

    public void setEmplacementSuiv(String emplacementSuiv) {
        this.emplacementSuiv = emplacementSuiv;
    }

    public String getHoraireClose() {
        return horaireClose;
    }

    public void setHoraireClose(String horaireClose) {
        this.horaireClose = horaireClose;
    }

    public String getHoraireCloseSuiv() {
        return horaireCloseSuiv;
    }

    public void setHoraireCloseSuiv(String horaireCloseSuiv) {
        this.horaireCloseSuiv = horaireCloseSuiv;
    }

    public String getHoraireOpen() {
        return horaireOpen;
    }

    public void setHoraireOpen(String horaireOpen) {
        this.horaireOpen = horaireOpen;
    }

    public String getHoraireOpenSuiv() {
        return horaireOpenSuiv;
    }

    public void setHoraireOpenSuiv(String horaireOpenSuiv) {
        this.horaireOpenSuiv = horaireOpenSuiv;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLatitudeSuiv() {
        return latitudeSuiv;
    }

    public void setLatitudeSuiv(Double latitudeSuiv) {
        this.latitudeSuiv = latitudeSuiv;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLongitudeSuiv() {
        return longitudeSuiv;
    }

    public void setLongitudeSuiv(Double longitudeSuiv) {
        this.longitudeSuiv = longitudeSuiv;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public static final Parcelable.Creator<CoffeeShopModel> CREATOR = new Parcelable.Creator<CoffeeShopModel>() {
        @Override
        public CoffeeShopModel createFromParcel(Parcel in) {
            return new CoffeeShopModel(in);
        }

        @Override
        public CoffeeShopModel[] newArray(int size) {
            return new CoffeeShopModel[size];
        }
    };


    @Override
    public String toString() {
        return "ViaFerrataModel{" +
                "nom='" + nom + '\'' +
                ", latitude=" + latitude + '\'' +
                ", longitude=" + longitude + '\'' +
                ", description='" + description + '\'' +
                ", emplacement='" + emplacement + '\'' +
                ", emplacementSuiv='" + emplacementSuiv + '\'' +
                ", latitudeSuiv='" + latitudeSuiv + '\'' +
                ", longitudeSuiv='" + longitudeSuiv + '\'' +
                ", horaireOpen='" + horaireOpen + '\'' +
                ", horaireOpenSuiv='" + horaireOpenSuiv + '\'' +
                ", horaireClose='" + horaireClose + '\'' +
                ", horaireCloseSuiv='" + horaireCloseSuiv + '\'' +
                ", contact='" + contact + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nom);
        parcel.writeString(emplacement);
        parcel.writeString(emplacementSuiv);
        parcel.writeString(horaireOpen);
        parcel.writeString(horaireOpenSuiv);
        parcel.writeString(horaireClose);
        parcel.writeString(horaireCloseSuiv);
        parcel.writeString(contact);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitudeSuiv);
        parcel.writeDouble(longitudeSuiv);
        parcel.writeString(description);
        parcel.writeString(photoUrl);
    }



    // Méthode servant à lire les données du parcelable
    private CoffeeShopModel(Parcel in) {
        nom = in.readString();
        emplacement  = in.readString();
        emplacementSuiv  = in.readString();
        horaireOpen  = in.readString();
        horaireOpenSuiv  = in.readString();
        horaireClose  = in.readString();
        horaireCloseSuiv  = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        latitudeSuiv = in.readDouble();
        longitudeSuiv = in.readDouble();
        description = in.readString();
        contact = in.readString();
        photoUrl = in.readString();
    }
}
