package mobiledimension.exchangerates.presenter.SocialNetwork;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnPostingCompleteListener;
import com.github.gorbin.asne.vk.VkSocialNetwork;
import com.vk.sdk.VKScope;


import mobiledimension.exchangerates.ui.SocialNetwork.SocialNetworkView;

@InjectViewState
public class SocialNetwork extends MvpPresenter<SocialNetworkView> {

    private final String VK_KEY = "6040457";
    private String[] vkScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.STATUS,
    };
    private OnPostingCompleteListener postingComplete = new OnPostingCompleteListener() {
        @Override
        public void onPostSuccessfully(int socialNetworkID) {
            try {
                //нет необходимости
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
            getViewState().showToast("Error while sending: " + errorMessage);
        }
    };


    public void vkShare(SocialNetworkManager socialNetworkManager, Bitmap screenshot) {
        int networkId = 0; //на случай если будут кнопки от других соц сетей
        networkId = VkSocialNetwork.ID;
        com.github.gorbin.asne.core.SocialNetwork socialNetwork = socialNetworkManager.getSocialNetwork(networkId);
        if (!socialNetwork.isConnected()) {
            if (networkId != 0) {
                socialNetwork.requestLogin();
                getViewState().showToast("После авторизации нажмите повторно на публикацию");
            } else {
                getViewState().showToast("Wrong networkId");
            }
        } else {
            Bundle postParams = new Bundle();
            postParams.putString(com.github.gorbin.asne.core.SocialNetwork.BUNDLE_LINK, "https://fixer.io");

            socialNetwork.requestPostPhotoMessageLink(screenshot, postParams, "ExchangeRates", postingComplete);
            getViewState().showToast("Опубликовано");
        }
    }


    public String getVK_KEY() {
        return VK_KEY;
    }

    public String[] getVkScope() {
        return vkScope;
    }


}
