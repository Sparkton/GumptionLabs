package com.example.gumptionlabs;

import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.regex.Matcher;

public class VideoPlayer extends AppCompatActivity {
    private static String vidUrl = "samplevideo";
    private VideoView videoView;
    private int currentPosition = 0;
    private static final String PLAYBACK = "playback";
    String vimeoVideo = "<html><body><iframe src=\"https://player.vimeo.com/video/163996646\" width=\"420\" height=\"315\" frameborder=\"0\" allowfullscreen></iframe></body></html>";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video_player);
//        videoView = findViewById(R.id.myWebView);
//        if (savedInstanceState != null) {
//            currentPosition = savedInstanceState.getInt(PLAYBACK);
//        }
//        MediaController controller = new MediaController(this);
//        controller.setMediaPlayer(videoView);
//        videoView.setMediaController(controller);
//        String vimeoVideo = "<html><body><iframe width=\"420\" height=\"315\" src=\"https://player.vimeo.com/video/163996646?player_id=player\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
//
//        WebView webView = (WebView)findViewById(R.id.myWebView);
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
//
//                webView.loadUrl(request.getUrl().toString());
//                return true;
//            }
//        });
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.loadData(vimeoVideo, "text/html", "utf-8");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        String vimeoVideo = "<html><body><iframe width=\"420\" height=\"315\" src=\"https://player.vimeo.com/video/163996646?player_id=player\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        WebView webView = (WebView)findViewById(R.id.myWebView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {

                webView.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadData(vimeoVideo, "text/html", "utf-8");
    }

    @Override
    protected void onStart() {
        super.onStart();

//Load the media every time onStart() is called//

        initializePlayer();

//Start playing the video//

        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

//If our app is on running on API level 23 or lower….//

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {

//...then pause the video whenever onPause() is called//

            videoView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

//Stop the video and release all resources held by the VideoView//

        releasePlayer();
    }

//Create an initializePlayer() method//

    private void initializePlayer() {

//Set the URI that the VideoView should play//

        Uri videoUri = getMedia(vidUrl);
        videoView.setVideoURI(videoUri);

    }

//Release all resources//

    private void releasePlayer() {
        videoView.stopPlayback();
    }

//Retrieve the video file and convert it into a URI//

    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            return Uri.parse(mediaName);
        } else {
            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + mediaName);
        }
    }
}