package renderEngine;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;


/**
 * Created by Victor on 2016-04-09.
 */
public class DisplayManager {
    private long window;

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;

    private Loader loader = new Loader();
    private Renderer renderer = new Renderer();

    public void createDisplay(){
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        if ( glfwInit() != GLFW_TRUE ){
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(WIDTH, HEIGHT, "HELLO WORLD", NULL, NULL);
        if(window == NULL){
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ){
                    glfwSetWindowShouldClose(window, GLFW_TRUE);
                }

                if(key == GLFW_KEY_LEFT){
                    glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
                }
                if(key == GLFW_KEY_RIGHT){
                    glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
                }
                if(key == GLFW_KEY_UP){
                    glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
                }
                if(key == GLFW_KEY_DOWN){
                    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                }
            }
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (vidmode.width() - WIDTH) / 2,
                (vidmode.height() - HEIGHT) / 2
        );

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        glfwShowWindow(window);
    }

    public void updateDisplay(){
        GL.createCapabilities();

//        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);



        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,

                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f,
        };

        glRenderMode(GL_RENDER);
        RawModel model = loader.loadToVAO(vertices);

        while ( glfwWindowShouldClose(window) == GLFW_FALSE ) {
            renderer.prepare();
            renderer.render(model);

//            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(window); // swap the color buffers
            glfwPollEvents();

        }
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        try {
            createDisplay();
            updateDisplay();

            loader.cleanUp();
            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            glfwTerminate();
            errorCallback.release();
        }
    }
}
