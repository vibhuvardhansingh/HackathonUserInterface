package info.andriodhive.hackathonuser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class MapsCameraFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match


    public MapsCameraFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_maps_camera, container, false);
        Button button = view.findViewById(R.id.btn_add);
        ImageView imageView = view.findViewById(R.id.im_map);

        button.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(),CameraActivity.class);
            startActivity(i);
        });
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),MapsCameraFragment.class);
            startActivity(intent);
        });
        return view;
    }

}
