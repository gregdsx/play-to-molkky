package com.devtoweb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 *
 * @author Greg27
 */
public class InstructionActivity extends FragmentActivity {

    private final LinearLayout.LayoutParams lpMatchParent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private final LinearLayout.LayoutParams lpWrapContent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private LinearLayout generalWrapper;
    private PagerAdapter pageAdapter;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        int paddingGeneralWrapper = ViewsMaker.getDpFromPixel(this, 20);

        generalWrapper = new LinearLayout(this);
        generalWrapper.setOrientation(LinearLayout.VERTICAL);
        generalWrapper.setLayoutParams(lpMatchParent);
        generalWrapper.setBackgroundResource(R.drawable.background);
        generalWrapper.setGravity(Gravity.CENTER_HORIZONTAL);
        generalWrapper.setPadding(paddingGeneralWrapper, paddingGeneralWrapper, paddingGeneralWrapper, paddingGeneralWrapper);

        //Définition de la vue à travers generalWrapper
        setContentView(generalWrapper);

        LinearLayout titleWrapper = new LinearLayout(this);
        titleWrapper.setLayoutParams(lpWrapContent);
        titleWrapper.setGravity(Gravity.CENTER);
        generalWrapper.addView(titleWrapper);

        TextView title = new TextView(this);
        title.setText("Comment jouer au Mölkky ?");
        title.setTextColor(Color.BLACK);
        title.setTextSize(25);
        titleWrapper.addView(title);

        //Conteneur des instructions
        //Gestionnaire de pages qui permet de cumuler plusieurs fragments (pages)
        ViewPager instructionsWrapper = new ViewPager(this);
        instructionsWrapper.setLayoutParams(lpMatchParent);
        instructionsWrapper.setId(8451254);
        generalWrapper.addView(instructionsWrapper);

        //Liste des fragments (pages)
        ArrayList<Fragment> slides = new ArrayList<Fragment>();

        //Ajout des fragments (pages)
        slides.add(Fragment.instantiate(this, FirstSlide.class.getName()));
        slides.add(Fragment.instantiate(this, SecondSlide.class.getName()));
        slides.add(Fragment.instantiate(this, ThirdSlide.class.getName()));
        slides.add(Fragment.instantiate(this, FourthSlide.class.getName()));

        //Ajout des fragments à l'adaptateur qui permet la navigation (pages)
        pageAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), slides);

        //Adaptation du ViewPager grace à pageAdapter pour la naviagtion entre les fragments (pages)
        instructionsWrapper.setAdapter(pageAdapter);

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(InstructionActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    //INNER CLASSES
    /**
     * Configuration de l'adaptateur grace à FragmentManager et liste des fragments
     */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        //liste des fragments (pages)
        private final ArrayList<Fragment> slides;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> slides) {
            super(fm);
            this.slides = slides;
        }

        public Fragment getItem(int i) {
            return this.slides.get(i);
        }

        @Override
        public int getCount() {
            return this.slides.size();
        }

    }

    /**
     * Premiere règle (fragment)
     */
    public static class FirstSlide extends Fragment {

        private final LinearLayout.LayoutParams lpMatchParent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        @Override
        public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle bundle) {

            ScrollView scroll = new ScrollView(getContext());

            LinearLayout rule1wrapper = new LinearLayout(getContext());
            rule1wrapper.setLayoutParams(lpMatchParent);
            rule1wrapper.setGravity(Gravity.CENTER);
            int paddingLeftRightRule1 = ViewsMaker.getDpFromPixel(getContext(), 5);
            int paddingTopRule1 = ViewsMaker.getDpFromPixel(getContext(), 20);
            rule1wrapper.setPadding(paddingLeftRightRule1, paddingTopRule1, paddingLeftRightRule1, 0);
            scroll.addView(rule1wrapper);

            TextView rule1 = new TextView(getContext());
            rule1.setText("Le principe du jeu est de faire tomber les quilles en bois à l’aide du lanceur appelé Mölkky. Les quilles sont marquées de 1 à 12. Le premier ou la première à totaliser exactement 50 points gagne la partie.\n"
                    + "\n" + "Au début d’une partie, les quilles sont placées à environ 4 mètres des joueurs. Lorsqu’une quille a été abattue, on la redresse sur son pied (et non à la tête), le numéro face à la zone de lancer, juste là où elle se trouve et sans la soulever du sol. C’est ainsi qu’au cours de la partie, les quilles s’éparpillent et s’éloignent.");
            rule1.setTextColor(Color.BLACK);
            rule1.setTextSize(16);
            rule1wrapper.addView(rule1);

            return scroll;
        }

    }

    /**
     * Deuxieme règle (fragment)
     */
    public static class SecondSlide extends Fragment {

        private final LinearLayout.LayoutParams lpMatchParent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        private final LinearLayout.LayoutParams lpWrapContent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        @Override
        public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle bundle) {

            ScrollView scroll = new ScrollView(getContext());

            LinearLayout rule2wrapper = new LinearLayout(getContext());
            rule2wrapper.setLayoutParams(lpMatchParent);
            rule2wrapper.setGravity(Gravity.CENTER);
            rule2wrapper.setOrientation(LinearLayout.VERTICAL);
            int paddingLeftRightRule2 = ViewsMaker.getDpFromPixel(getContext(), 5);
            int paddingTopRule2 = ViewsMaker.getDpFromPixel(getContext(), 20);
            rule2wrapper.setPadding(paddingLeftRightRule2, paddingTopRule2, paddingLeftRightRule2, 0);
            scroll.addView(rule2wrapper);

            TextView rule2 = new TextView(getContext());
            rule2.setText("Le placement des quilles au départ de la partie : " + "\n");
            rule2.setTextColor(Color.BLACK);
            rule2.setTextSize(16);
            rule2wrapper.addView(rule2);

            ImageView bowling = new ImageView(getContext());
            bowling.setLayoutParams(lpWrapContent);
            bowling.setBackgroundResource(R.drawable.molkky_dispo);
            int paddingTopBottomImg = ViewsMaker.getDpFromPixel(getContext(), 20);
            bowling.setPadding(0, paddingTopBottomImg, 0, paddingTopBottomImg);
            rule2wrapper.addView(bowling);
            
            TextView points = new TextView(getContext());
            points.setText("\n Il y a deux façons de marquer des points : \n" + "\n" + "- Si un joueur fait tomber plusieurs quilles, il gagne autant de points que de quilles abattues \n"
                    + "\n" + "- Si un joueur fait tomber une seule quille, il gagne autant de points que le nombre inscrit dessus.");                    
            points.setTextColor(Color.BLACK);
            points.setTextSize(16);
            rule2wrapper.addView(points);

            return scroll;
        }

    }

    /**
     * Troisième règle (fragment)
     */
    public static class ThirdSlide extends Fragment {

        private final LinearLayout.LayoutParams lpMatchParent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        @Override
        public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle bundle) {

            ScrollView scroll = new ScrollView(getContext());

            LinearLayout rule3wrapper = new LinearLayout(getContext());
            rule3wrapper.setLayoutParams(lpMatchParent);
            rule3wrapper.setGravity(Gravity.CENTER);
            rule3wrapper.setOrientation(LinearLayout.VERTICAL);
            int paddingLeftRightRule3 = ViewsMaker.getDpFromPixel(getContext(), 5);
            int paddingTopRule3 = ViewsMaker.getDpFromPixel(getContext(), 20);
            rule3wrapper.setPadding(paddingLeftRightRule3, paddingTopRule3, paddingLeftRightRule3, 0);
            scroll.addView(rule3wrapper);

            TextView newRule = new TextView(getContext());
            newRule.setText("Régles spéciales :" + "\n");
            newRule.setTextSize(16);
            newRule.setTextColor(Color.BLACK);
            rule3wrapper.addView(newRule);

            TextView rule3 = new TextView(getContext());
            rule3.setText("- Une quille n’est considérée comme abattue que si elle est tombée entièrement sur le sol et ne repose sur aucune autre \n"
                    + "\n" + "- Si un joueur manque un lancer, il ne marque donc pas de points et obtient une croix. Si le joueur manque trois lancers consécutifs, il obtient 3 croix et est éliminé de la partie. \n"
                    + "Un lancer réussi remet les croix à zéro (maximum 2 croix)\n" + "\n"
                    + "- Si un joueur dépasse 50 points, il retombe immédiatement à 25 points \n" + "\n"
                    + "- Le Mölkky (lanceur) doit être lancé d'un geste du bas vers le haut, comme au bowling. Le Mölkky peut rebondir sur le sol ou toucher directement les quilles");
            rule3.setTextColor(Color.BLACK);
            rule3.setTextSize(14);
            rule3wrapper.addView(rule3);

            return scroll;
        }

    }

    /**
     * Quatrieme règle (fragment) Règle pour la prochaine version de play to molkky
     */
    public static class FourthSlide extends Fragment {

        private final LinearLayout.LayoutParams lpMatchParent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        @Override
        public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle bundle) {

            ScrollView scroll = new ScrollView(getContext());

            LinearLayout rule4wrapper = new LinearLayout(getContext());
            rule4wrapper.setLayoutParams(lpMatchParent);
            rule4wrapper.setGravity(Gravity.CENTER);
            rule4wrapper.setOrientation(LinearLayout.VERTICAL);
            int paddingLeftRightRule4 = ViewsMaker.getDpFromPixel(getContext(), 5);
            int paddingTopRule4 = ViewsMaker.getDpFromPixel(getContext(), 20);
            rule4wrapper.setPadding(paddingLeftRightRule4, paddingTopRule4, paddingLeftRightRule4, 0);
            scroll.addView(rule4wrapper);

            TextView titleRule = new TextView(getContext());
            titleRule.setText("Comment utiliser Play To Mölkky ? :" + "\n");
            titleRule.setTextSize(16);
            titleRule.setTextColor(Color.BLACK);
            rule4wrapper.addView(titleRule);

            TextView rule4 = new TextView(getContext());
            rule4.setText("Play To Mölkky est un compte score pour le jeu Mölkky. Vous l'utiliserez tout au long de vos parties de Mölkky "
                    + "pour vous rappeler l'ordre de jeu, les points et le nombre de croix de chaque joueurs. Mais également à savoir "
                    + "qui est éliminé et qui gagne la partie ! \n");
            rule4.setTextColor(Color.BLACK);
            rule4.setTextSize(14);
            rule4wrapper.addView(rule4);

            TextView titleRule2 = new TextView(getContext());
            titleRule2.setText("Fonctionnement général :" + "\n");
            titleRule2.setTextSize(16);
            titleRule2.setTextColor(Color.BLACK);
            rule4wrapper.addView(titleRule2);

            TextView rule42 = new TextView(getContext());
            rule42.setText("- L'ordre de jeu de la première partie dépend de l'ordre des noms des joueurs enregistrés \n"
                    + "\n" + "- Pour marquer les points des joueurs, sélectionnez les quilles tombées lors du lancé du joueur (ou sélectionnez uniquement la quille corresondante au score) et appuyez sur OK pour valider le score du joueur.\n"
                    + "\n" + "- Le lancé précédent peut être annulé si une erreur a été commise lors de l'enregistrement du score précédent. Attention vous ne pouvez effectué qu'un seul retour à la fois (vous ne pouvez revenir qu'au joueur précédent le joueur actuel).\n"
                    + "\n" + "- Quand la partie se termine, un classement est effectué du premier au dernier puis les éliminés. Si vous recommencez une partie, cet ordre sera utilisé pour favoriser les perdants de la manche précédente.\n"
            );
            rule42.setTextColor(Color.BLACK);
            rule42.setTextSize(14);
            rule4wrapper.addView(rule42);

            return scroll;
        }

    }
    /**
     * Cinquieme règle (fragment)
     */
    public static class FifthSlide extends Fragment {

        private final LinearLayout.LayoutParams lpMatchParent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        @Override
        public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle bundle) {

            ScrollView scroll = new ScrollView(getContext());

            LinearLayout rule3wrapper = new LinearLayout(getContext());
            rule3wrapper.setLayoutParams(lpMatchParent);
            rule3wrapper.setGravity(Gravity.CENTER);
            rule3wrapper.setOrientation(LinearLayout.VERTICAL);
            rule3wrapper.setPadding(5, 20, 5, 0);
            scroll.addView(rule3wrapper);

            TextView newRule = new TextView(getContext());
            newRule.setText("Comment utiliser Play to Mölkky :" + "\n");
            newRule.setTextSize(16);
            newRule.setTextColor(Color.BLACK);
            rule3wrapper.addView(newRule);

            TextView rule3 = new TextView(getContext());
            rule3.setText("- Une quille n’est considérée comme abattue que si elle est tombée entièrement sur le sol et ne repose sur aucune autre \n"
                    + "\n" + "- Si un joueur manque un lancer, il ne marque donc pas de points et obtient une croix. Si le joueur manque trois lancers consécutifs, il obtient 3 croix et est éliminé de la partie. \n"
                    + "Un lancer réussi remet les croix à zéro (maximum 2 croix)\n" + "\n"
                    + "- Si un joueur dépasse 50 points, il retombe immédiatement à 25 points \n" + "\n"
                    + "- Le Mölkky (lanceur) doit être lancé d'un geste du bas vers le haut, comme au bowling. Le Mölkky peut rebondir sur le sol ou toucher directement les quilles");
            rule3.setTextColor(Color.BLACK);
            rule3.setTextSize(14);
            rule3wrapper.addView(rule3);

            return scroll;
        }

    }

}
