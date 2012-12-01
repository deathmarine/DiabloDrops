package de.bananaco.bookapi.lib;

import java.util.List;

import net.minecraft.server.ItemStack;

/**
 * API for modifying, viewing, and generally having
 * fun with books
 */
public interface Book
{

    public int max_lines = 13;

    /**
     * Returns the author of the book (or null if not set)
     * 
     * @return String (author)
     */
    public String getAuthor();

    public ItemStack getItemStack();

    /**
     * Returns the pages of the book (or null if not set)
     * 
     * @return List<String> (pages)
     */
    public List<String> getListPages();

    /**
     * Returns the pages of the book (or null if not set)
     * 
     * @return String[] (pages)
     */
    public String[] getPages();

    /**
     * Returns the title of the book (or null if not set)
     * 
     * @return String (title)
     */
    public String getTitle();

    /**
     * Replicates the call to check if a Book has an author
     * 
     * @return boolean (hasAuthor)
     */
    public boolean hasAuthor();

    /**
     * Replicates the call to check if a Book has pages
     * 
     * @return boolean (hasPages)
     */
    public boolean hasPages();

    /**
     * Replicates the call to check if a Book has a title
     * 
     * @return boolean (hasTitle)
     */
    public boolean hasTitle();

    /**
     * Sets the author of the book
     * 
     * @param author
     */
    public void setAuthor(String author);

    /**
     * Sets the pages of the book
     * 
     * @param pages
     */
    public void setPages(List<String> pages);

    /**
     * Sets the pages of the book
     * 
     * @param pages
     */
    public void setPages(String[] pages);

    /**
     * Sets the title of the book
     * 
     * @param title
     */
    public void setTitle(String title);

}
