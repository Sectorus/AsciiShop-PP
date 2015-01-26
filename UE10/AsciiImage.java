import  java.util.ArrayList;

class AsciiImage {

	//Benoetigte Variabeln
	private char[][] lineArray = null;
	private int width;
	private int height;
	private int hCount = 0;
	private char cTarget = 'o';
	private int xTarget = 0;
	private int yTarget = 0;
	boolean load = false;
	String charset = "";

	//Image-Konstruktor - dabei wird ein bereits vorhandenes Image auf ein neu erzeugtes Image angewendet. Die dazu benoetigten Werte
	//koennen dem uebergebenen Image entnommen werden.
	public AsciiImage(AsciiImage img)
	{
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.lineArray = new char[height][width];
		this.charset = img.charset;

		for (int i = 0; i < this.height; i++)
		{
			for (int j = 0; j < this.width; j++)
			{
				this.lineArray[i][j] = img.getPixel(j, i);
			}
		}
	}

	//Image-Konstruktor - hier wird ein Image mittels Dimensionen und Zeichensatz erzeugt.
	public AsciiImage(int width, int height, String charset)
	{

		this.width = width;
		this.height = height;
		this.lineArray = new char[height][width];
		this.charset = charset;

		//Die Uebergabeparameter werden auf Korrektheit ueberprueft. Tritt ein Fehler auf, wird eine IllegalArgumentException geworfen.
		if ((width <= 0) || (height <= 0) || (charset.length() <= 0))
		{
			IllegalArgumentException err = new IllegalArgumentException();
			throw err;
		}

		for (int i = 0; i < charset.length(); i++)
		{
			if (charset.subSequence(i + 1, charset.length()).toString().contains("" + charset.charAt(i)))
			{
				IllegalArgumentException err = new IllegalArgumentException();
				throw err;
			}
		}



		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				lineArray[i][j] = charset.charAt(charset.length() - 1);
			}

		}
	}

	//Gibt den Zeichensatz zurueck.
	public String getCharset()
	{
		return charset;
	}

	//Gibt den Breite zurueck.
	public int getWidth() {
		return width;
	}

	//Gibt den Hoehe zurueck.
	public int getHeight() {
		return height;
	}

	//Wandelt das Ascii-Bild in einen einzigen String um und gibt in zurueck.
	public String toString() {
		String s = "";
		for (int i = 0; i < height; i++) {
			s += String.valueOf(lineArray[i]);

			if(i != (height-1))
			{
				s += '\n';
			}
		}
		// System.out.println(height+","+width);
		return s;
	}

	//Gibt Anzahl der unterschiedlichen Zeichen aus
	public int getUniqueChars()
	{

		int uniqueChars = 0;
		String buffer = "";
		for(int i = 1; i<height; i++)
		{
			if(lineArray[i]!=null)
			{
				for (int j = 0; j < width; j++) {
					if (!buffer.contains(String.valueOf(lineArray[i][j]))) {
						buffer += lineArray[i][j];
						uniqueChars++;
					}

				}

			}
		}
		return uniqueChars;
	}

	//Ersetzt alle Zeichen des Bildes durch einen Punkt (.). Dadurch wir das Bild 'geloescht'.
	public void clear() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				lineArray[i][j] = '.';
			}
		}
	}

	//Ersetzt ein spezielles Zeichen durch ein neues Zeichen
	public void replace(char oldChar, char newChar) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (lineArray[i][j] == oldChar) {
					lineArray[i][j] = newChar;
				}
			}
		}
	}

	//Gibt eine Liste aller Punkt (Koordinaten) zurueck, welche das gesuchte Zeichen darstellen.
	public ArrayList<AsciiPoint> getPointList(char c)
	{
		ArrayList<AsciiPoint> al = new ArrayList<AsciiPoint>();
		for (int i = 0; i < lineArray.length; i++)
		{
			for (int j = 0; j < lineArray[i].length; j++)
			{
				if (lineArray[i][j] == c) { al.add(new AsciiPoint(j, i)); }
			}
		}
		return al;
	}

	//Setzt einen Punkt auf ein spezielles Zeichen.
	public void setPixel(int x, int y, char c)
	{
		if ((x > width) || (y > height) || !charset.contains(c + ""))
		{
			IndexOutOfBoundsException err = new IndexOutOfBoundsException();
			throw err;
		}
		lineArray[y][x] = c;
	}

	//Zerlegt einen Punkt der Klasse AsciiPoint und gibt dessen Daten an setPixel() weiter.
	public void setPixel(AsciiPoint p, char c)
	{
		if ((p.getX() > width) || (p.getY() > height) || !charset.contains(c + ""))
		{
			IndexOutOfBoundsException err = new IndexOutOfBoundsException();
			throw err;
		}
		setPixel(p.getX(), p.getY(), c);
	}

	//Gibt das Zeichen eines Punktes zurueck
	public char getPixel(int x, int y)
	{
		if ((x > width) || (y > height))
		{
			IndexOutOfBoundsException err = new IndexOutOfBoundsException();
			throw err;
		}
		return this.lineArray[y][x];
	}

	//Gibt das Zeichen eines Punkte-Objekts zurueck
	public char getPixel(AsciiPoint p)
	{
		if ((p.getX() > width) || (p.getY() > height))
		{
			IndexOutOfBoundsException err = new IndexOutOfBoundsException();
			throw err;
		}
		return getPixel(p.getX(), p.getY());
	}

	//Gibt den "hash"-Wert des Ascii-Bildes zurück
	public int hashCode()
	{
		int hashWert = 0;
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				hashWert += getPixel(i, j);
			}
		}
		return hashWert;
	}

	//Vergleich von Ascii-Bildern
	public boolean equals(Object o)
	{
		if (this.getClass() != o.getClass()) 
		{ 
			return false; 
		}


		AsciiImage other = (AsciiImage) o; //Object cast auf AsciiImage
		
		if (this.getHeight() != other.getHeight() || this.getWidth() != other.getWidth()) 
		{
			return false;
		}
		for(int i = 0; i < height; i++) 
		{
			for(int j = 0; j < width; j++) 
			{
				if(getPixel(i, j) != other.getPixel(i, j)) 
					{ 
						return false; 
					}
			}
		}
		return true;
	}
}
