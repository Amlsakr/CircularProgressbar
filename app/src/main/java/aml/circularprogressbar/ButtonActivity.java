package aml.circularprogressbar;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

public class ButtonActivity extends AppCompatActivity {
    int pStatus = 0;
    private Handler handler = new Handler();
    TextView tv;
    ImageView imageView ;
    int max = 34 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);
        final ProgressBar mProgress =  findViewById(R.id.Progressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(max); // Secondary Progress
        mProgress.setMax(max); // Maximum Progress
        mProgress.setProgressDrawable(drawable);

      /*  ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, 100);
        animation.setDuration(50000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();*/

        tv =  findViewById(R.id.tvbt);
        imageView = findViewById(R.id.button);

        final MediaPlayer mp = new MediaPlayer();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i,1);

                if(mp.isPlaying())
                {
                    mp.stop();
                }

                try {
                    mp.reset();
                    AssetFileDescriptor afd;
                    afd = getAssets().openFd("show.mp3");
                    mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    mp.prepare();
                    mp.start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }





if (pStatus   == max)
    return ;


                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                            pStatus += 1;

                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    mProgress.setProgress(pStatus);

                                    tv.setText(pStatus  + "");

                                }
                            });
                            try {
                                // Sleep for 200 milliseconds.
                                // Just to display the progress slowly
                                Thread.sleep(1); //thread will take approx 1.5 seconds to finish
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                }).start();

            }
        });

    }
}
