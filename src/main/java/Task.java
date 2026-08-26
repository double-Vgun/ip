public class Task {
    private String description ="";
    private boolean isDone =false;
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public boolean isDone() {
        return isDone;
    }
    public String getStatus(){
        if (this.isDone){
            return "[X] " + getDescription();
        }
        else{
            return "[ ] " + getDescription();
        }
    }
}
