package Controlador;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerController extends FragmentPagerAdapter {
    int numoftabs;
    //Constructor

    public PagerController(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numoftabs = behavior;
    }

    //Metodos
    @NonNull
    @Override
    public Fragment getItem(int position) {
        //ponemos un switch
        switch (position){
            //caso 0 retornar gastos
            case 0:
                return new TotalGastos();

                //caso 1 para retornar acerca de
            case 1:
                return new AcercaDe();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        //Retorna el nuemro de tabs
        return numoftabs;
    }
}
