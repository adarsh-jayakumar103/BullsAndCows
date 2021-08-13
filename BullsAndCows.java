import java.awt.*;  
import java.awt.event.*;  
import javax.swing.JOptionPane; 

public class BullsAndCows extends Frame implements ActionListener
{
	final Button b[]=new Button[9];
	final Button reveal, undo, enter;
	int n[][]=new int[22][4];
	int p[]=new int[22];
	int ans[]=new int[4];
	int B[]=new int[22];
	int C[]=new int[22];	
	int guess=1, check=0;
	float col[]={0.11f,0.28f,0.5f,0.6f,0.8f,0.15f,0.42f,0.72f,0.99f};
	BullsAndCows()
	{
		super("Bulls and Cows");
		//Initialising answer
		initAns();
		//Adding buttons
		for(int i=0;i<9;++i)
		{
		
			b[i]=new Button(""+(i+1));
			b[i].setBounds(735,150+(40*i),30,30);
			b[i].setBackground(Color.getHSBColor(col[i],i>5?0.7f:1,i>5?0.8f:1));
			b[i].addActionListener(this);
			add(b[i]);
		}
		
		setSize(800,700);
		
		reveal=new Button("Reveal");
		reveal.setBounds(720,600,60,32);
		reveal.addActionListener(this);
		add(reveal);

		undo=new Button("Undo");
		undo.setBounds(720,560,60,32);
		undo.addActionListener(this);
		add(undo);
	
		enter=new Button("Enter");
		enter.setBounds(720,520,60,32);
		enter.addActionListener(this);
		add(enter);

		//Other settings
		setLayout(null);
		setVisible(true);
		addWindowListener(new WindowAdapter() 
		{
      			public void windowClosing(WindowEvent e) 
			{
        			System.exit(0);
      			}
    		});
		
	}

	public void initAns()
	{
		for(int i=0;i<4;++i)
			ans[i]=(int)(Math.random()*10);
		while(ans[0]==0)
			ans[0]=(int)(Math.random()*10);
		for(int i=0;i<4;++i)
			for(int j=0;j<i;++j)
				while(ans[i]==ans[j] || ans[i]==0)
					ans[i]=(int)(Math.random()*10);
		
		System.out.println(""+ans[0]+ans[1]+ans[2]+ans[3]);

	}

	public void actionPerformed(ActionEvent e)
	{
		for(int i=0;i<9;++i)
		{
			if(e.getSource()==b[i])
			{
				if(guess>1&&check==0&&p[guess-1]>=4)
					JOptionPane.showMessageDialog(this,"Click on Enter first"); 
				else
				{
					n[guess-1][p[guess-1]++] = Integer.parseInt(b[i].getLabel());
					check=0;
				}
				break;
			}
		}
		if(e.getSource()==undo)
		{
			if(p[guess-1]>0)
			{
				
				if(--p[guess-1]==0)
					check=2;
			}
			else
			{
				JOptionPane.showMessageDialog(this,"Cannot undo further");
				check=0;	
			}
		}
		if(e.getSource()==reveal)
		{
			JOptionPane.showMessageDialog(this,"GAME OVER\nThe answer was: "+ans[0]+ans[1]+ans[2]+ans[3]);
			initAns();
			n=new int[22][4];
			p=new int[22];
			B=new int[22];
			C=new int[22];	
			guess=1;
			check=0;
			
		}
		if(e.getSource()==enter)
		{
			if(p[guess-1]!=4)
			{
				JOptionPane.showMessageDialog(this,"Enter entire guess");
				if(check==1)
					check=2;
			}
			else
			{
				check=1;
			}
		}
		repaint();
	}
		
	public void paint(Graphics g)
	{
		g.setColor(Color.black);
		g.drawRect(30,50,680,620);
		g.fillRect(355,50,2,620);
		g.drawLine(130,50,130,670);
		g.drawLine(455,50,455,670);
		g.drawLine(30,100,710,100);
		g.setFont(new Font("Helvetica",Font.PLAIN,12));
		g.drawString("Bulls and Cows",36,80);
		g.drawString("Your Guess",150,80);
		g.drawString("Bulls and Cows",360,80);
		g.drawString("Your Guess",480,80);
		for(int i=0;i<guess;++i)
		{
			for(int j=0;j<p[i];++j)
			{
				g.setColor(Color.getHSBColor(col[n[i][j]-1],(n[i][j]-1)>5?0.7f:1,(n[i][j]-1)>5?0.8f:1));
				g.fillRect((i<11?135:460)+(j*40),620-(i%11*50),30,30);
				g.setColor(Color.black);
				g.drawString(""+n[i][j],(i<11?146:325+146)+(j*40),640-(i%11*50));
			}
			if(i>0)
			{
				if(C[i-1]+B[i-1]==0)
					g.drawLine(i-1<12?40:362,625-(50*((i-1)%11))+10,(i-1<11?40:362)+55,625-(50*((i-1)%11))+10);
				for(int j=0;j<B[i-1];++j)
				{
					g.drawRect((i<12?40:362)+(15*j),630-(50*((i-1)%11)),10,10); //Bull
				}
				for(int j=0;j<C[i-1];++j)
				{
					g.fillRect((i<12?40:362)+(15*(j+B[i-1])),630-(50*((i-1)%11)),11,11); //Cow
				}
			}
		}
		if(check==1)
		{
			
			g.setColor(Color.black);
			for(int i=0;i<4;++i)
				if(ans[i]==n[guess-1][i])
					B[guess-1]++;
			for(int i=0;i<4;++i)
            			for(int j=0;j<4;++j)
                			if(n[guess-1][i]==ans[j])
                			 	C[guess-1]++;
			C[guess-1]-=B[guess-1];
			if(C[guess-1]+B[guess-1]==0)
				g.drawLine(guess-1<11?40:362,625-(50*((guess-1)%11))+10,(guess-1<11?40:362)+55,625-(50*((guess-1)%11))+10);
			for(int j=0;j<B[guess-1];++j)
			{
				g.drawRect((guess-1<11?40:362)+(15*j),630-(50*((guess-1)%11)),10,10); //Bull
			}
			for(int j=0;j<C[guess-1];++j)
			{
				g.fillRect((guess-1<11?40:362)+(15*(j+B[guess-1])),630-(50*((guess-1)%11)),11,11); //Cow
			}
			if(B[guess-1]==4)
			{
				JOptionPane.showMessageDialog(this,"Congratulations!!\nYou won in "+guess+ "tries!");
				initAns();
				n=new int[22][4];
				p=new int[22];
				B=new int[22];
				C=new int[22];	
				guess=0;
				check=0;
			}
			guess++;
			
		}
	}

	public static void main(String args[])
	{
		new BullsAndCows();
	}
}

