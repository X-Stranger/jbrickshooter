package by.jbrickshooter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            GridLayout rootView = (GridLayout) inflater.inflate(R.layout.fragment_main, container, false);
            Context context = rootView.getContext();

            Point point = new Point();
            this.getActivity().getWindowManager().getDefaultDisplay().getSize(point);
            int width = point.x;
            int height = point.y;
            System.out.println("HEIGHT: " + height);
            System.out.println("WIDTH: " + width);
            int size = height > width ? width : height;
            size /= 16;

            GridLayout.Spec cornerLeft = GridLayout.spec(0, 3);
            GridLayout.Spec cornerRight = GridLayout.spec(13, 3);
            GridLayout.Spec spanTop = GridLayout.spec(0, 3);
            GridLayout.Spec spanBottom = GridLayout.spec(13, 3);

            ImageView item = new ImageView(context);
            item.setBackgroundColor(Color.RED);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(cornerLeft, spanTop);
            params.width = size * 3;
            params.height = size * 3;
            item.setLayoutParams(params);
            rootView.addView(item);

            for (int j = 0; j < 3; j++) {
                for (int i = 3; i < 13; i++) {
                    item = new ImageView(context);
                    item.setBackgroundColor(Color.rgb((i + j) * 5, (i + j) * 10, (i + j) * 15));
                    params = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));
                    params.width = size;
                    params.height = size;
                    item.setLayoutParams(params);
                    rootView.addView(item);
                }
            }

            item = new ImageView(context);
            item.setBackgroundColor(Color.GREEN);
            params = new GridLayout.LayoutParams(cornerRight, spanTop);
            params.width = size * 3;
            params.height = size * 3;
            item.setLayoutParams(params);
            rootView.addView(item);

            item = new ImageView(context);
            item.setBackgroundColor(Color.YELLOW);
            params = new GridLayout.LayoutParams(cornerLeft, spanBottom);
            params.width = size * 3;
            params.height = size * 3;
            item.setLayoutParams(params);
            rootView.addView(item);

            item = new ImageView(context);
            item.setBackgroundColor(Color.BLUE);
            params = new GridLayout.LayoutParams(cornerRight, spanBottom);
            params.width = size * 3;
            params.height = size * 3;
            item.setLayoutParams(params);
            rootView.addView(item);

            return rootView;
        }
    }

}
