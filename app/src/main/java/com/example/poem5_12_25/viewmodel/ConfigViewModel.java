package com.example.poem5_12_25.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.poem5_12_25.MainApp;

/**
 * 全局配置viewmodel类
 * @Classname ConfigViewModel
 * @Description TODO
 * @Author Huan
 * @Date 2020/12/27 18:57
 * @Version 1.0
 */
public class ConfigViewModel extends ViewModel {
    /**
     * 是否云端上传
     */
    private MutableLiveData<Boolean> _cloud_update = new MutableLiveData<>();
    private LiveData<Boolean> cloud_update;

    private LiveData<Boolean> register_refresh;
    private MutableLiveData<Boolean> _register_refresh= new MutableLiveData<>();

    private LiveData<Boolean> home_refresh;
    private MutableLiveData<Boolean> _home_refresh= new MutableLiveData<>();

    private LiveData<Boolean> poem_refresh ;
    private MutableLiveData<Boolean> _poem_refresh= new MutableLiveData<>();

    private LiveData<Boolean> clear_all ;
    private MutableLiveData<Boolean> _clear_all= new MutableLiveData<>();

    private LiveData<Boolean> night_mode ;
    private MutableLiveData<Boolean> _night_mode= new MutableLiveData<>();

    private LiveData<Boolean> login_refresh;
    private MutableLiveData<Boolean> _login_refresh = new MutableLiveData<>();

    private LiveData<Boolean> max_text;
    private MutableLiveData<Boolean> _max_text = new MutableLiveData<>();


    public ConfigViewModel() {
        SharedPreferences preferences1 = MainApp.getInstance().getSharedPreferences("usersetting", Context.MODE_PRIVATE);
//        _register_refresh.setValue(preferences1.getBoolean("register_refresh",Boolean.FALSE));
//        _home_refresh.postValue(preferences1.getBoolean("home_refresh",Boolean.FALSE));
//        _poem_refresh.postValue(preferences1.getBoolean("poem_refresh",Boolean.FALSE));
//        _clear_all.postValue(preferences1.getBoolean("clear_all",Boolean.FALSE));
//        _max_text.postValue(preferences1.getBoolean("max_text",Boolean.FALSE));
//        _night_mode.postValue(preferences1.getBoolean("night_mode",Boolean.FALSE));
//        _login_refresh.postValue(preferences1.getBoolean("login_refresh",Boolean.FALSE));
//        _cloud_update.postValue(preferences1.getBoolean("cloud_upload",Boolean.FALSE));
        _register_refresh.setValue(preferences1.getBoolean("register_refresh",Boolean.FALSE));
        _home_refresh.setValue(preferences1.getBoolean("home_refresh",Boolean.FALSE));
        _poem_refresh.setValue(preferences1.getBoolean("poem_refresh",Boolean.FALSE));
        _clear_all.setValue(preferences1.getBoolean("clear_all",Boolean.FALSE));
        _max_text.setValue(preferences1.getBoolean("max_text",Boolean.FALSE));
        _night_mode.setValue(preferences1.getBoolean("night_mode",Boolean.FALSE));
        _login_refresh.setValue(preferences1.getBoolean("login_refresh",Boolean.FALSE));
        _cloud_update.setValue(preferences1.getBoolean("cloud_upload",Boolean.FALSE));
        this.cloud_update= _cloud_update;
        this.register_refresh = _register_refresh;
        this.home_refresh = _home_refresh;
        this.poem_refresh = _poem_refresh;
        this.clear_all = _clear_all;
        this.night_mode = _night_mode;
        this.login_refresh = _login_refresh;
        this.max_text = _max_text;
    }

    public LiveData<Boolean> getCloud_update() {
        return _cloud_update;
    }

    public void setCloud_update(MutableLiveData<Boolean> cloud_update) {
        this.cloud_update = cloud_update;
        cloud_update = _cloud_update;
    }

    /**
     * postvalue改变livedata值
     * @param bool
     */
    public void setCloud_update(Boolean bool) {
        Log.d("是否上传用户信息已经变为:",bool.toString());
        _cloud_update.postValue(bool);
        cloud_update = _cloud_update;
    }

    public void setCloud_update(LiveData<Boolean> cloud_update) {
        this.cloud_update = cloud_update;
    }

    public LiveData<Boolean> getRegister_refresh() {
        return register_refresh;
    }

    public void setRegister_refresh(LiveData<Boolean> register_refresh) {
        this.register_refresh = register_refresh;
    }

    public void setRegister_refresh(Boolean bool) {
        this._register_refresh.postValue(bool);
        this.register_refresh = _register_refresh;
    }

    public LiveData<Boolean> getHome_refresh() {
        return home_refresh;
    }

    public void setHome_refresh(LiveData<Boolean> home_refresh) {
        this.home_refresh = home_refresh;
    }

    public void setHome_refresh(Boolean bool) {
        this._home_refresh.postValue(bool);
        this.home_refresh = _home_refresh;
    }

    public LiveData<Boolean> getPoem_refresh() {
        return poem_refresh;
    }

    public void setPoem_refresh(LiveData<Boolean> poem_refresh) {
        this.poem_refresh = poem_refresh;
    }
    public void setPoem_refresh(Boolean bool) {
        this._poem_refresh.postValue(bool);
        this.poem_refresh = _poem_refresh;
    }

    public LiveData<Boolean> getClear_all() {
        return clear_all;
    }

    public void setClear_all(LiveData<Boolean> clear_all) {
        this.clear_all = clear_all;
    }
    public void setClear_all(Boolean bool) {
        this._clear_all.postValue(bool);
        this.clear_all = _clear_all;
    }

    public LiveData<Boolean> getNight_mode() {
        return night_mode;
    }

    public void setNight_mode(LiveData<Boolean> night_mode) {
        this.night_mode = night_mode;
    }
    public void setNight_mode(Boolean bool) {
        this._night_mode.postValue(bool);
        this.night_mode = _night_mode;
    }

    public LiveData<Boolean> getLogin_refresh() {
        return login_refresh;
    }

    public void setLogin_refresh(LiveData<Boolean> login_refresh) {
        this.login_refresh = login_refresh;
    }

    public void setLogin_refresh(Boolean bool) {
        this._login_refresh.postValue(bool);
        this.login_refresh = _login_refresh;
    }

    public LiveData<Boolean> getMax_text() {
        return max_text;
    }

    public void setMax_text(LiveData<Boolean> max_text) {
        this.max_text = max_text;
    }
    public void setMax_text(Boolean bool) {
        this._max_text.postValue(bool);
        this.max_text = _max_text;
    }
}
