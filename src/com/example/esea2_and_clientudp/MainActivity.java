package com.example.esea2_and_clientudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.example.esea2_and_clientudp.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView numeroText ;
	EditText IPAddressEditText ;
	;
    

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		numeroText = (TextView) findViewById(R.id.LO_numeroRicevuto);
		IPAddressEditText = (EditText) findViewById(R.id.LO_indirizzoIp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void  inviaRichiesta(View v)  throws UnknownHostException,IOException
	{
		new InviaERiceve().execute();
	}

	
	private class InviaERiceve extends AsyncTask<Void, Void, String> 
	{
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result)
        {
        	numeroText.setText(result);
        }
		@Override
		protected String doInBackground(Void... params)
		{
			DatagramSocket socket;
			
			byte[] dati = new byte[10];
			try 
			{
				
				socket = new DatagramSocket ();
				
				InetAddress address = InetAddress.getByName (IPAddressEditText.getText().toString());
				DatagramPacket packetDaInviare = new DatagramPacket ("REQ".getBytes(), "REQ".length(), address, 23365);
				socket.send (packetDaInviare);
				DatagramPacket packetDaRicevere = new DatagramPacket (dati,10);
				socket.receive(packetDaRicevere);
				socket.close();
		            
		            
			}
			catch (SocketException e) { e.printStackTrace(); } 
			catch (IOException e) { e.printStackTrace(); }

			return new String(dati);
		}
    }
	
}
