public class Enemy {
	private int x,y; 
	private int type; 
	private int currentFrame; 
	private int totalFrames;
	
	public Enemy(int type, int x, int y, int totalFrames){
		this.type = type; 
		this.x = x;
		this.y = y;
		this.totalFrames = totalFrames; 
		this.currentFrame = 0; 
	}
	public void updateAnimation() {
		currentFrame = (currentFrame + 1) % totalFrames; 
	}
	//Getters
	public int getX() {return x;}
	public int getY() {return y;}
	public int getType() {return type;}
	public int getCurrentFrame() {return currentFrame;}
	
	//setters - to move enemy 
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
}