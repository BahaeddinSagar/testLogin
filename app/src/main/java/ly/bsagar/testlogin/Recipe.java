package ly.bsagar.testlogin;

public class Recipe {
    public int id;
    public String name;
    public String ingredients;
    public String preparing_method;
    public String image;
    public String nutritionist_id;


    public Recipe(int id, String name, String ingredients, String preparing_method, String image, String nutritionist_id) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.preparing_method = preparing_method;
        this.image = image;
        this.nutritionist_id = nutritionist_id;
    }
}
