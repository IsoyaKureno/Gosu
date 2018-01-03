package fr.wcs.gosu;

import android.os.Parcel;
import android.os.Parcelable;

class CardModel implements Parcelable{
    private String name,clan;
    private Integer rank,mutationCost;
    private String[] laidAbilities,linkAbilities,mutationAbilities;

    CardModel(String name, String clan, Integer rank,
              Integer mutationCost, String[] laidAbilities,
              String[] linkAbilities, String[] mutationAbilities){
        this.name=name;
        this.clan=clan;
        this.rank=rank;
        this.mutationCost=mutationCost;
        this.laidAbilities=laidAbilities;
        this.linkAbilities=linkAbilities;
        this.mutationAbilities=mutationAbilities;
    }

    private CardModel(Parcel in) {
        name = in.readString();
        clan = in.readString();
        if (in.readByte() == 0) {
            rank = null;
        } else {
            rank = in.readInt();
        }
        if (in.readByte() == 0) {
            mutationCost = null;
        } else {
            mutationCost = in.readInt();
        }
        laidAbilities = in.createStringArray();
        linkAbilities = in.createStringArray();
        mutationAbilities = in.createStringArray();
    }

    public static final Creator<CardModel> CREATOR = new Creator<CardModel>() {
        @Override
        public CardModel createFromParcel(Parcel in) {
            return new CardModel(in);
        }

        @Override
        public CardModel[] newArray(int size) {
            return new CardModel[size];
        }
    };

    String getName() {return name;}

    String getClan() {return clan;}

    Integer getRank() {return rank;}
    Integer getPower() {
        switch (rank){
            case 1 : return 2;
            case 2 : return 3;
            case 3 : return 5;
            default : return 0;
        }
    }

    Integer getMutationCost() {return mutationCost;}

    String[] getLaidAbilities() {return laidAbilities;}

    String[] getLinkAbilities() {return linkAbilities;}

    String[] getMutationAbilities() {return mutationAbilities;}

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(clan);
        if (rank == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(rank);
        }
        if (mutationCost == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mutationCost);
        }
        parcel.writeStringArray(laidAbilities);
        parcel.writeStringArray(linkAbilities);
        parcel.writeStringArray(mutationAbilities);
    }
}
