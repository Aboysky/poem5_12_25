package com.example.poem5_12_25.activity;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.poem5_12_25.R;
import com.example.poem5_12_25.cache.LastPoemCache;
import com.example.poem5_12_25.entity.Poem;
import com.example.poem5_12_25.utils.http.HttpRequestUtil;
import com.example.poem5_12_25.utils.http.tool.HttpRequestData;
import com.example.poem5_12_25.utils.http.tool.HttpResponseData;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.stream.Collectors;

public class PoemActivity extends AppCompatActivity {

    private static final String TAG = "PoemActivity";

    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ctl_toolbar)
    CollapsingToolbarLayout ctlToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.sv_content)
    ScrollView svContent;
    @BindView(R.id.fab_refresh)
    FloatingActionButton fabRefresh;
    @BindView(R.id.fab_favorite)
    FloatingActionButton fabFavorite;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private Poem currentPoem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 状态栏颜色处理
        StatusBarUtil.setTransparent(PoemActivity.this);
        setContentView(R.layout.activity_poem);

        ButterKnife.bind(this);

        ViewCompat.setNestedScrollingEnabled(svContent, true);
        setSupportActionBar(toolbar);

        setCurrentPoem(new Poem(0,
                getString(R.string.poem_author),
                getString(R.string.poem_title),
                getString(R.string.poem_content).replace('\n', ',')
        ));

    }



    /**
     * 设置当前的古诗
     *
     * @param poem Poem对象
     */
    private void setCurrentPoem(Poem poem) {
        currentPoem = poem;
        tvAuthor.setText(poem.getAuthor());
        tvTitle.setText(poem.getName());
        // 根据不同安卓系统版本做的优化
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            tvContent.setText(String.join("\n", poem.getContent()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvContent.setText(poem.getContent().stream().collect(Collectors.joining("\n")));
        } else {
            StringBuilder joinContent = new StringBuilder();
            for (String s : poem.getContent()) {
                joinContent.append(s).append("\n");
            }
            // 删除最后一个换行符
            joinContent.deleteCharAt(joinContent.length() - 1);
            tvContent.setText(joinContent);
        }
    }

    /**
     * 加载刷新古诗
     */
    class GetPoemTask extends AsyncTask<Void, Integer, HttpResponseData> {

        @Override
        protected HttpResponseData doInBackground(Void... voids) {
            HttpRequestData request = new HttpRequestData("http:localhost:5000/getpoem");
            return HttpRequestUtil.getData(request);
        }

        @Override
        protected void onPostExecute(HttpResponseData data) {
            super.onPostExecute(data);
            if (data.success) {
                try {
                    JSONObject jsonObject = new JSONObject(data.content);
                    Poem poem = Poem.parseJsonString(jsonObject.getJSONObject("data").toString());
                    poem.setContent(jsonObject.getJSONObject("data").getJSONArray("content"));
                    LastPoemCache.setLastPoem(poem);
                    setCurrentPoem(poem);
                } catch (JSONException ex) {
                    Log.e(TAG, ex.getMessage());
                }
            } else
                Snackbar.make(coordinatorLayout, "网络连接失败！", Snackbar.LENGTH_SHORT).show();
        }
    }


}
