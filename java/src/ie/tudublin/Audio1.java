package ie.tudublin;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class Audio1 extends PApplet
{

    Minim minim;
    AudioInput ai;
    AudioPlayer ap;
    AudioBuffer ab;

	public void settings()
	{
		size(1024, 500);
	}

    int frameSize = 1024;

	public void setup() {
		colorMode(HSB);
		background(0);

        minim = new Minim(this);

        ai = minim.getLineIn(Minim.MONO, frameSize, 44100, 16);
        ab = ai.mix;
		
		smooth();
		
	}

	
	
	public void draw()
	{	
		background(0);
        stroke(255);
        float average;
        float sum;
        float smoothedAmplitude;
        float lerpedBuffer[];
        int halfH[];
        int mode;

        float half = height / 2;
        float cgap = 255 / (float)ab.size();

        float total = 0;
        for(int i = 0 ; i < ab.size() ; i ++)
        {
            total += abs(ab.get(i));
            stroke(cgap * i, 255, 255);
            line(i, half, i , half + ab.get(i) * half); 
        }
        average= sum / (float) ab.size();

        smoothedAmplitude = lerp(smoothedAmplitude, average, 0.1f);
        
        float cx = width / 2;
        float cy = height / 2;

        switch (mode) {
			case 0:
                background(0);
                for(int i = 0 ; i < ab.size() ; i ++)
                {
                    //float c = map(ab.get(i), -1, 1, 0, 255);
                    float c = map(i, 0, ab.size(), 0, 255);
                    stroke(c, 255, 255);
                    float f = lerpedBuffer[i] * halfH * 4.0f;
                    line(i, halfH + f, i, halfH - f);                    
                }
                break;
        case 1:
            background(0);
            for(int i = 0 ; i < ab.size() ; i ++)
            {
                //float c = map(ab.get(i), -1, 1, 0, 255);
                float c = map(i, 0, ab.size(), 0, 255);
                stroke(c, 255, 255);
                float f = lerpedBuffer[i] * halfH * 4.0f;
                line(i, halfH + f, halfH - f, i);                    
            }
            break;      
        case 2:
            background(0);
            for(int i = 0 ; i < ab.size() ; i ++)
            {
                float c = map(i, 0, ab.size(), mouseX /2, mouseY/ 2);
                stroke(c, 255, 255);
                float f = lerpedBuffer[i] * halfH * 4.0f;
                line(0, i, f, i);              
                line(width, i, width - f, i);              
                line(i, 0, i, f);          
                line(i, height, i, height - f);              
            }
            break;          
        }


        
        float r = average * 200;
        lerpedR = lerp(lerpedR, r, 0.1f);

        circle(100, 200, lerpedR);
	}

    float lerpedR = 0;
}