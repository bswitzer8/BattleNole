package bswitzer.android.com.battlenole;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by James Harper on 0024, July 24, 2016.
 */
public class NetworkClient extends Thread{

    private static final int PORT = 5469;

    private static final String SERVER_ADDRESS = "68.35.233.185";

    private DataInputStream inputStream;

    private DataOutputStream outputStream;

    private Socket clientSocket;

    private String input;

    private boolean isMyTurn = false;

    private  final String CLASS_NAME = this.getName();

    private Activity mainActvitiy;

    public NetworkClient(Activity mainActivity){
        this.mainActvitiy = mainActivity;

    }

    @Override
    public void run(){
        try{
            clientSocket = new Socket(SERVER_ADDRESS, PORT);

            mainActvitiy.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(mainActvitiy);

                }
            });

            inputStream = new DataInputStream(clientSocket.getInputStream());

            outputStream = new DataOutputStream(clientSocket.getOutputStream());

        }catch (IOException e){
            Log.d(CLASS_NAME, e.toString());

        }


    }

        public void sendMove(String move){

            if(isMyTurn){
                try {
                    outputStream.writeUTF(move);

                }catch(IOException e) {
                    Log.d(CLASS_NAME, "Unable to write move to socket");
                }

            }




    }
    public String getMove() throws IOException {

       return inputStream.readUTF();
    }

    private void showToast(Context ctx) {
        Toast.makeText(ctx, "Hi!", Toast.LENGTH_SHORT).show();
    }



    }



