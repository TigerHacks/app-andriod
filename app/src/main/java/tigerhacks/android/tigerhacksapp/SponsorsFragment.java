package tigerhacks.android.tigerhacksapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SponsorsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SponsorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SponsorsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View layout;
    private HomeScreenActivity home;
    private ImageView im;

    private ImageView im1;

    private OnFragmentInteractionListener mListener;

    public SponsorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SponsorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SponsorsFragment newInstance(String param1, String param2) {
        SponsorsFragment fragment = new SponsorsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_sponsors, container, false);
        home = (HomeScreenActivity) getActivity();
        Log.e("TEST", "view loaded");
        return layout;
    }

    public void onStart()
    {
        super.onStart();
        home.onFragmentsReady();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void loadSponsorData(final SponsorList list) {
        final LinearLayout pLayout = layout.findViewById(R.id.platinumLayout);
        LinearLayout gLayout = layout.findViewById(R.id.goldLayout);
        LinearLayout sLayout = layout.findViewById(R.id.silverLayout);
        LinearLayout bLayout = layout.findViewById(R.id.bronzeLayout);

        for(Sponsor sponsor : list.getSponsors())
        {
            ImageView image = new ImageView(getContext());
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)gLayout.getLayoutParams();
            layoutParams.height = dpToPx(100);
            image.setLayoutParams(layoutParams);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Picasso.get().load(sponsor.getImage()).into(image);
            switch(sponsor.getLevel())
            {
                case "Platinum":
                    pLayout.addView(image);
                    break;
                case "Gold":
                    gLayout.addView(image);
                    break;
                case "Silver":
                    sLayout.addView(image);
                    break;
                case "Bronze":
                    bLayout.addView(image);
                    break;
            }

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("TEST", "clicked image");
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popView = inflater.inflate(R.layout.fragment_sponsors_detail, null);
                    final PopupWindow window = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    /*if(Build.VERSION.SDK_INT>=21){
                        window.setElevation(5.0f);
                    }*/

                    ImageView backButton = popView.findViewById(R.id.backButton);
                    popView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            window.dismiss();
                        }
                    });
                    window.showAtLocation(pLayout, Gravity.CENTER,10,10);
                    window.update();
                }
            });

        }
    }

    private int dpToPx(int dp) {
        float density = getContext().getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
}
