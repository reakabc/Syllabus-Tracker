package com.abc.reak.syllabustracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    CardView physics, chem, math, bio;
    TextView percentage_phys, percentage_chem, percentage_math, percentage_bio, percentage, percentage_jee, percentage_neet;

    SharedPreferenceConfig sharedPreferenceConfig;
    DatabaseHelper db;

    ProgressBar pMath, pPhy, pChem, pBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        sharedPreferenceConfig = new SharedPreferenceConfig(this);

        if (sharedPreferenceConfig.readIsFirstTimeUser()){
            firstTimeInitialization();
            sharedPreferenceConfig.writeIsFirstTimeUser(false);
        }

        physics = findViewById(R.id.physics);
        chem = findViewById(R.id.chemistry);
        math = findViewById(R.id.maths);
        bio = findViewById(R.id.biology);

        pMath = findViewById(R.id.progress_bar_math);
        pPhy = findViewById(R.id.progress_bar_phy);
        pChem = findViewById(R.id.progress_bar_che);
        pBio = findViewById(R.id.progress_bar_bio);

        percentage = findViewById(R.id.percentage);
        percentage_phys = findViewById(R.id.percentage_phy);
        percentage_chem = findViewById(R.id.percentage_chem);
        percentage_math = findViewById(R.id.percentage_math);
        percentage_bio = findViewById(R.id.percentage_bio);
        percentage_jee = findViewById(R.id.percentage_jee);
        percentage_neet = findViewById(R.id.percentage_neet);

        calculate();

        physics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent BIOLOGY = new Intent(MainActivity.this, ChapterListActivity.class);
                BIOLOGY.putExtra("subject", Subjects.PHYSICS);
                startActivity(BIOLOGY);

            }
        });

        chem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent BIOLOGY = new Intent(MainActivity.this, ChapterListActivity.class);
                BIOLOGY.putExtra("subject", Subjects.CHEMISTRY);
                startActivity(BIOLOGY);

            }
        });

        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent BIOLOGY = new Intent(MainActivity.this, ChapterListActivity.class);
                BIOLOGY.putExtra("subject", Subjects.MATH);
                startActivity(BIOLOGY);

            }
        });

        bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent BIOLOGY = new Intent(MainActivity.this, ChapterListActivity.class);
                BIOLOGY.putExtra("subject", Subjects.BIOLOGY);
                startActivity(BIOLOGY);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        calculate();
    }

    public void calculate(){

        final float[] phy = db.getCompletedPercentage(Subjects.PHYSICS);
        final float[] chem = db.getCompletedPercentage(Subjects.CHEMISTRY);
        final float[] math = db.getCompletedPercentage(Subjects.MATH);
        final float[] bio = db.getCompletedPercentage(Subjects.BIOLOGY);

        float ncert = phy[0]+chem[0]+math[0]+bio[0];
        float jee = phy[1]+chem[1]+math[1];
        float neet = phy[2]+chem[2]+(bio[2]/2);

        percentage_phys.setText(phy[0]+" / 100");
        pPhy.setProgress((int) phy[0]);
        percentage_chem.setText(chem[0]+" / 100");
        pChem.setProgress((int) chem[0]);
        percentage_math.setText(math[0]+" / 100");
        pMath.setProgress((int) math[0]);
        percentage_bio.setText(bio[0]+" / 100");
        pBio.setProgress((int) bio[0]);

        percentage.setText(Math.round(ncert/4)+" / 100");
        percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPieChart((int) phy[0], (int) chem[0], (int) math[0], (int) bio[0]);
            }
        });
        percentage_jee.setText(Math.round(jee/3)+" / 100");
        percentage_jee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPieChart((int) phy[1], (int) chem[1], (int) math[1], 0);
            }
        });

        percentage_neet.setText(Math.round(neet/3)+" / 100");
        percentage_neet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPieChart(0, (int) phy[2], (int) chem[2], (int) bio[2]);
            }
        });

    }

    private void showPieChart(int a, int b, int c, int d) {

        final android.app.Dialog dialog = new android.app.Dialog(this);
        dialog.setContentView(R.layout.dialog_pie_chart_status);
        dialog.setCancelable(true);
        setData(dialog, a, b, c, d);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnim;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    private void setData(Dialog dialog, int a, int b, int c, int d)
    {

        PieChart pieChart = dialog.findViewById(R.id.pie_chart);
        TextView a_ = dialog.findViewById(R.id.tvMath);
        TextView b_ = dialog.findViewById(R.id.tvPhy);
        TextView c_ = dialog.findViewById(R.id.tvChe);
        TextView d_ = dialog.findViewById(R.id.tvBio);

        // Set the percentage of language used
        a_.setText(String.valueOf(a));
        b_.setText(String.valueOf(b));
        c_.setText(String.valueOf(c));
        d_.setText(String.valueOf(d));

        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Mathematics",
                        a,
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Physics",
                        b,
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "Chemistry",
                        c,
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Biology",
                        d,
                        Color.parseColor("#29B6F6")));

        // To animate the pie chart
        pieChart.startAnimation();
    }

    public void firstTimeInitialization(){

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        
        //Organic
        databaseHelper.addOne(new Chapter(1, "General Organic Chemistry", Subjects.CHEMISTRY, 3f, 3f, 5f, false, false));
        databaseHelper.addOne(new Chapter(2, "Hydrocarbons", Subjects.CHEMISTRY, 3f, 3f, 4f, false, false));
        databaseHelper.addOne(new Chapter(3, "Aldehydes, Ketones and Carboxylic Acids", Subjects.CHEMISTRY, 3f, 3f, 3f, false, false));
        databaseHelper.addOne(new Chapter(4, "Haloalkane", Subjects.CHEMISTRY, 3f, 3f, 2f, false, false));
        databaseHelper.addOne(new Chapter(5, "Alkyl Halide, Alcohol & Ether", Subjects.CHEMISTRY, 3f, 3f, 4f, false, false));
        databaseHelper.addOne(new Chapter(6, "Polymer", Subjects.CHEMISTRY, 3f, 3f, 2f, false, false));
        databaseHelper.addOne(new Chapter(7, "Aromatic Compound", Subjects.CHEMISTRY, 3f, 3f, 6f, false, false));
        databaseHelper.addOne(new Chapter(8, "Carbonyl Compound", Subjects.CHEMISTRY, 3f, 3f, 4f, false, false));
        databaseHelper.addOne(new Chapter(9, "Biomolecules", Subjects.CHEMISTRY, 3f, 3f, 4f, false, false));
        databaseHelper.addOne(new Chapter(10, "Organic Compound Containing Nitrogen", Subjects.CHEMISTRY, 3f, 3f, 3f, false, false));
        databaseHelper.addOne(new Chapter(11, "Environmental Chemistry", Subjects.CHEMISTRY, 3f, 3f, 2f, false, false));
        databaseHelper.addOne(new Chapter(12, "Chemistry in Everyday Life", Subjects.CHEMISTRY, 3f, 3f, 2f, false, false));
        databaseHelper.addOne(new Chapter(13, "Practical Organic Chemistry", Subjects.CHEMISTRY, 3f, 3f, 2f, false, false));
        databaseHelper.addOne(new Chapter(14, "UIPAC & Isomerism", Subjects.CHEMISTRY, 3f, 3f, 4f, false, false));

        //Inorganic
        databaseHelper.addOne(new Chapter(15, "Periodic Table & Periodicity in Properties", Subjects.CHEMISTRY, 3f, 3f, 4f, false, false));
        databaseHelper.addOne(new Chapter(16, "Chemical Bonding", Subjects.CHEMISTRY, 3f, 3f, 9f, false, false));
        databaseHelper.addOne(new Chapter(17, "s-Block", Subjects.CHEMISTRY, 3f, 3f, 3f, false, false));
        databaseHelper.addOne(new Chapter(18, "p-Block", Subjects.CHEMISTRY, 3f, 3f, 7f, false, false));
        databaseHelper.addOne(new Chapter(19, "Hydrogen", Subjects.CHEMISTRY, 3f, 3f, 2f, false, false));
        databaseHelper.addOne(new Chapter(20, "Qualitative Analysis", Subjects.CHEMISTRY, 3f, 3f, 2f, false, false));
        databaseHelper.addOne(new Chapter(21, "Metallurgy", Subjects.CHEMISTRY, 3f, 3f, 2f, false, false));
        databaseHelper.addOne(new Chapter(22, "d-Block & f-Block Elements", Subjects.CHEMISTRY, 3f, 3f, 4f, false, false));
        databaseHelper.addOne(new Chapter(23, "Coordination Compounds", Subjects.CHEMISTRY, 3f, 3f, 6f, false, false));

        //Physical
        databaseHelper.addOne(new Chapter(24, "Thermodynamics & Thermochemistry", Subjects.CHEMISTRY, 3f, 3f, 3f, false, false));
        databaseHelper.addOne(new Chapter(25, "Mole Concept", Subjects.CHEMISTRY, 3f, 3f, 5f, false, false));
        databaseHelper.addOne(new Chapter(26, "Gaseous State", Subjects.CHEMISTRY, 3f, 3f, 3f, false, false));
        databaseHelper.addOne(new Chapter(27, "Redox Reactions", Subjects.CHEMISTRY, 3f, 3f, 2f, false, false));
        databaseHelper.addOne(new Chapter(28, "Ionic Equilibrium", Subjects.CHEMISTRY, 3f, 3f, 4f, false, false));
        databaseHelper.addOne(new Chapter(29, "Chemical Equilibrium", Subjects.CHEMISTRY, 3f, 3f, 3f, false, false));
        databaseHelper.addOne(new Chapter(30, "Atomic Structure And Nuclear Chemistry", Subjects.CHEMISTRY, 3f, 3f, 3f, false, false));
        databaseHelper.addOne(new Chapter(31, "Surface Chemistry", Subjects.CHEMISTRY, 2f, 2f, 2f, false, false));
        databaseHelper.addOne(new Chapter(32, "Solution & Cogitative Properties", Subjects.CHEMISTRY, 2f, 2f, 4f, false, false));
        databaseHelper.addOne(new Chapter(33, "Solid State", Subjects.CHEMISTRY, 2f, 2f, 3f, false, false));
        databaseHelper.addOne(new Chapter(34, "Electrochemistry", Subjects.CHEMISTRY, 2f, 2f, 3f, false, false));
        databaseHelper.addOne(new Chapter(35, "Chemical Kinetics", Subjects.CHEMISTRY, 2f, 2f, 4f, false, false));

        //Zoology
        databaseHelper.addOne(new Chapter(36, "Human Health & Diseases", Subjects.BIOLOGY, 5f, 0f, 9f, false, false));
        databaseHelper.addOne(new Chapter(37, "Animal Husbandry", Subjects.BIOLOGY, 5f, 0f, 3f, false, false));
        databaseHelper.addOne(new Chapter(38, "Origin & Evolution", Subjects.BIOLOGY, 5f, 0f, 10f, false, false));
        databaseHelper.addOne(new Chapter(39, "Human Reproduction & Reproductive Health", Subjects.BIOLOGY, 5f, 0f, 18f, false, false));
        databaseHelper.addOne(new Chapter(40, "Human Physiology", Subjects.BIOLOGY, 5f, 0f, 45f, false, false));
        databaseHelper.addOne(new Chapter(41, "Structural Organization in animals", Subjects.BIOLOGY, 5f, 0f, 2f, false, false));
        databaseHelper.addOne(new Chapter(42, "Animal Tissue", Subjects.BIOLOGY, 5f, 0f, 3f, false, false));
        databaseHelper.addOne(new Chapter(43, "Animal Diversity", Subjects.BIOLOGY, 5f, 0f, 10f, false, false));

        //Botany
        databaseHelper.addOne(new Chapter(44, "Ecology", Subjects.BIOLOGY, 5f, 0f, 16f, false, false));
        databaseHelper.addOne(new Chapter(45, "Biology in Human Welfare", Subjects.BIOLOGY, 5f, 0f, 2f, false, false));
        databaseHelper.addOne(new Chapter(46, "Genetics & Biotechnology", Subjects.BIOLOGY, 5f, 0f, 24f, false, false));
        databaseHelper.addOne(new Chapter(47, "Plant Reproduction", Subjects.BIOLOGY, 5f, 0f, 9f, false, false));
        databaseHelper.addOne(new Chapter(48, "Plant Physiology", Subjects.BIOLOGY, 5f, 0f, 13f, false, false));
        databaseHelper.addOne(new Chapter(49, "Bio-molecules", Subjects.BIOLOGY, 5f, 0f, 3f, false, false));
        databaseHelper.addOne(new Chapter(50, "Cell Biology & Cell Division", Subjects.BIOLOGY, 5f, 0f, 10f, false, false));
        databaseHelper.addOne(new Chapter(51, "Plant Morphology", Subjects.BIOLOGY, 5f, 0f, 7f, false, false));
        databaseHelper.addOne(new Chapter(52, "Plant Anatomy", Subjects.BIOLOGY, 10f, 0f, 4f, false, false));
        databaseHelper.addOne(new Chapter(53, "Plant Diversity", Subjects.BIOLOGY, 10f, 0f, 12f, false, false));

        //physics
        databaseHelper.addOne(new Chapter(54, "Wave Optics", Subjects.PHYSICS, 4f, 4f, 4f, false, false));
        databaseHelper.addOne(new Chapter(55, "Ray Optics & Optical Instruments", Subjects.PHYSICS, 4f, 4f, 5f, false, false));
        databaseHelper.addOne(new Chapter(56, "Dual Nature of Radiation and Matter", Subjects.PHYSICS, 4f, 4f, 4f, false, false));
        databaseHelper.addOne(new Chapter(57, "Atomic Nuclei", Subjects.PHYSICS, 4f, 4f, 5f, false, false));
        databaseHelper.addOne(new Chapter(58, "Semiconductor Electronics", Subjects.PHYSICS, 4f, 4f, 6f, false, false));
        databaseHelper.addOne(new Chapter(59, "Electromagnetic Induction", Subjects.PHYSICS, 4f, 4f, 2f, false, false));
        databaseHelper.addOne(new Chapter(60, "Current Electricity", Subjects.PHYSICS, 4f, 4f, 6f, false, false));
        databaseHelper.addOne(new Chapter(61, "Alternating Current", Subjects.PHYSICS, 4f, 4f, 3f, false, false));
        databaseHelper.addOne(new Chapter(62, "Electrostatics", Subjects.PHYSICS, 4f, 4f, 3f, false, false));
        databaseHelper.addOne(new Chapter(63, "Electrostatic Potential & Capacitance", Subjects.PHYSICS, 4f, 4f, 2f, false, false));
        databaseHelper.addOne(new Chapter(64, "Electromagnetic Wave", Subjects.PHYSICS, 4f, 4f, 1f, false, false));
        databaseHelper.addOne(new Chapter(65, "Electric Charge & Field", Subjects.PHYSICS, 4f, 4f, 2f, false, false));
        databaseHelper.addOne(new Chapter(66, "Thermodynamics", Subjects.PHYSICS, 4f, 4f, 7f, false, false));
        databaseHelper.addOne(new Chapter(67, "Magnetic Effect of Current & Magnetism", Subjects.PHYSICS, 4f, 4f, 6f, false, false));
        databaseHelper.addOne(new Chapter(68, "Thermal Properties of Matter", Subjects.PHYSICS, 4f, 4f, 2f, false, false));
        databaseHelper.addOne(new Chapter(69, "Properties of Bulk Matter", Subjects.PHYSICS, 4f, 4f, 3f, false, false));
        databaseHelper.addOne(new Chapter(70, "Kinetic Theory of Gases", Subjects.PHYSICS, 4f, 4f, 2f, false, false));
        databaseHelper.addOne(new Chapter(71, "Work, Energy and Power", Subjects.PHYSICS, 4f, 4f, 4f, false, false));
        databaseHelper.addOne(new Chapter(72, "Waves", Subjects.PHYSICS, 4f, 4f, 4f, false, false));
        databaseHelper.addOne(new Chapter(73, "Rotational Motion", Subjects.PHYSICS, 4f, 4f, 1f, false, false));
        databaseHelper.addOne(new Chapter(74, "Units & Measurements", Subjects.PHYSICS, 4f, 4f, 2f, false, false));
        databaseHelper.addOne(new Chapter(75, "Oscillations", Subjects.PHYSICS, 4f, 4f, 3f, false, false));
        databaseHelper.addOne(new Chapter(76, "System of Particle & Rigid Body", Subjects.PHYSICS, 2f, 2f, 7f, false, false));
        databaseHelper.addOne(new Chapter(77, "Center of Mass", Subjects.PHYSICS, 2f, 2f, 1f, false, false));
        databaseHelper.addOne(new Chapter(78, "Kinetics", Subjects.PHYSICS, 2f, 2f, 2f, false, false));
        databaseHelper.addOne(new Chapter(79, "Gravitation", Subjects.PHYSICS, 2f, 2f, 2f, false, false));
        databaseHelper.addOne(new Chapter(80, "Law of Motion", Subjects.PHYSICS, 2f, 2f, 7f, false, false));
        databaseHelper.addOne(new Chapter(81, "Mechanics of Solids and Fluids", Subjects.PHYSICS, 2f, 2f, 3f, false, false));

        //Mathematics
        databaseHelper.addOne(new Chapter(82, "Coordinate Geometry", Subjects.MATH, 5f, 20f, 0f, false, false));
        databaseHelper.addOne(new Chapter(83, "Limit, Continuity & Differentiability", Subjects.MATH, 5f, 12f, 0f, false, false));
        databaseHelper.addOne(new Chapter(84, "Integral Calculus", Subjects.MATH, 5f, 12f, 0f, false, false));
        databaseHelper.addOne(new Chapter(85, "Complex Numbers and Quadratic Equation", Subjects.MATH, 5f, 8f, 0f, false, false));
        databaseHelper.addOne(new Chapter(86, "Matrices and Determinants", Subjects.MATH, 5f, 8f, 0f, false, false));
        databaseHelper.addOne(new Chapter(87, "Statistics and Probability", Subjects.MATH, 5f, 8f, 0f, false, false));
        databaseHelper.addOne(new Chapter(88, "Three Dimensional Geometry", Subjects.MATH, 5f, 8f, 0f, false, false));
        databaseHelper.addOne(new Chapter(89, "Vector Algebra", Subjects.MATH, 5f, 8f, 0f, false, false));
        databaseHelper.addOne(new Chapter(90, "Sets, Relation and Function", Subjects.MATH, 5f, 4f, 0f, false, false));
        databaseHelper.addOne(new Chapter(91, "Permutations and Combinations", Subjects.MATH, 5f, 4f, 0f, false, false));
        databaseHelper.addOne(new Chapter(92, "Binomial Theorem and Its Application", Subjects.MATH, 5f, 4f, 0f, false, false));
        databaseHelper.addOne(new Chapter(93, "Sequences and Series", Subjects.MATH, 5f, 4f, 0f, false, false));
        databaseHelper.addOne(new Chapter(94, "Trigonometry", Subjects.MATH, 5f, 4f, 0f, false, false));
        databaseHelper.addOne(new Chapter(95, "Mathematical Reasoning", Subjects.MATH, 5f, 4f, 0f, false, false));
        databaseHelper.addOne(new Chapter(96, "Differential Equation", Subjects.MATH, 5f, 4f, 0f, false, false));
        databaseHelper.addOne(new Chapter(97, "Statics and Dynamics", Subjects.MATH, 10f, 4f, 0f, false, false));
        databaseHelper.addOne(new Chapter(98, "Differential Calculus", Subjects.MATH, 15f, 4f, 0f, false, false));

        

        /*databaseHelper.addOne(new Chapter(1, "Electrostatics & Electricity", Subjects.PHYSICS, 16.6f, 16.6f, 16.6f, false, false));
        databaseHelper.addOne(new Chapter(2, "Heat & Thermodynamics", Subjects.PHYSICS, 10.0f, 10.0f, 10.0f, false, false));
        databaseHelper.addOne(new Chapter(3, "Magnetism & EMI", Subjects.PHYSICS, 16.6f, 16.6f, 16.6f, false, false));
        databaseHelper.addOne(new Chapter(1, "Mechanics", Subjects.PHYSICS, 26.6f, 26.6f, 26.6f, false, false));
        databaseHelper.addOne(new Chapter(1, "Modern Physics", Subjects.PHYSICS, 10.0f, 10.0f, 10.0f, false, false));
        databaseHelper.addOne(new Chapter(1, "Optics", Subjects.PHYSICS, 13.3f, 13.3f, 13.3f, false, false));
        databaseHelper.addOne(new Chapter(1, "Waves", Subjects.PHYSICS, 6.6f, 6.6f, 6.6f, false, false));

        databaseHelper.addOne(new Chapter(1, "Organic Chemistry", Subjects.CHEMISTRY, 26.6f, 26.6f, 26.6f, false, false));
        databaseHelper.addOne(new Chapter(1, "Inorganic Chemistry", Subjects.CHEMISTRY, 26.6f, 26.6f, 26.6f, false, false));
        databaseHelper.addOne(new Chapter(1, "Physical Chemistry", Subjects.CHEMISTRY, 46.6f, 46.6f, 46.6f, false, false));


        databaseHelper.addOne(new Chapter(1, "Motion", Subjects.BIOLOGY, 1.0f, 0.5f, 0.5f, false, false));
        databaseHelper.addOne(new Chapter(1, "Motion", Subjects.BIOLOGY, 1.0f, 0.5f, 0.5f, false, false));
        databaseHelper.addOne(new Chapter(1, "Motion", Subjects.BIOLOGY, 1.0f, 0.5f, 0.5f, false, false));


        databaseHelper.addOne(new Chapter(1, "Calculus", Subjects.MATH, 24.6f, 26.6f, 26.6f, false, false));
        databaseHelper.addOne(new Chapter(1, "Trigonometry", Subjects.MATH, 6.6f, 6.6f, 6.6f, false, false));
        databaseHelper.addOne(new Chapter(1, "Inverse Trigonometry", Subjects.MATH, 2f, 0f, 0f, false, false));
        databaseHelper.addOne(new Chapter(1, "Algebra(XII)", Subjects.MATH, 6.6f, 6.6f, 6.6f, false, false));
        databaseHelper.addOne(new Chapter(1, "Algebra(XI)", Subjects.MATH, 23.3f, 23.3f, 23.3f, false, false));
        databaseHelper.addOne(new Chapter(1, "Coordinate Geometry", Subjects.MATH, 16.6f, 16.6f, 16.6f, false, false));
        databaseHelper.addOne(new Chapter(1, "Probability", Subjects.MATH, 3.3f, 3.3f, 3.3f, false, false));
        databaseHelper.addOne(new Chapter(1, "Statistics", Subjects.MATH, 3.3f, 3.3f, 3.3f, false, false));
        databaseHelper.addOne(new Chapter(1, "Reasoning", Subjects.MATH, 3.3f, 3.3f, 3.3f, false, false));
        databaseHelper.addOne(new Chapter(1, "3-D (XII)", Subjects.MATH, 6.6f, 6.6f, 6.6f, false, false));
        databaseHelper.addOne(new Chapter(1, "Vector", Subjects.MATH, 3.3f, 3.3f, 3.3f, false, false));*/

    }

}