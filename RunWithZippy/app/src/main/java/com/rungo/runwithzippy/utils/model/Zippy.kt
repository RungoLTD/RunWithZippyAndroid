package com.rungo.runwithzippy.utils.model

import android.content.Context
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.esotericsoftware.spine.*
import com.rungo.runwithzippy.utils.extensions.getScreenWidth
import javax.microedition.khronos.opengles.GL10

class Zippy(private val context: Context) : ApplicationAdapter() {

    private val camera: OrthographicCamera by lazy { OrthographicCamera() }
    private val batch: PolygonSpriteBatch by lazy { PolygonSpriteBatch() }
    private val renderer: SkeletonRenderer by lazy { SkeletonRenderer() }
    private val debugRenderer: SkeletonRendererDebug by lazy { SkeletonRendererDebug() }
    private lateinit var atlas: TextureAtlas
    private var skeleton: Skeleton? = null
    private var state: AnimationState? = null
    private lateinit var json: SkeletonJson

    override fun create() {
        renderer.premultipliedAlpha = true // PMA results in correct blending without outlines.
        debugRenderer.apply {
            setRegionAttachments(false)
            setMeshTriangles(false)
            setMeshHull(false)
        }

        atlas = TextureAtlas(Gdx.files.internal("coma/coma.atlas"))
        json = SkeletonJson(atlas) // This loads skeleton JSON data, which is stateless.
        json.scale = 0.5f // Load the skeleton at 60% the size it was in Spine.
        val skeletonData = json.readSkeletonData(Gdx.files.internal("coma/coma.json"))
        skeleton = Skeleton(skeletonData) // Skeleton holds skeleton state (bone positions, slot attachments, etc).
        skeleton?.setPosition((getScreenWidth(context) / 4).toFloat(), 0f)
        val stateData = AnimationStateData(skeletonData) // Defines mixing (crossfading) between animations.
//        stateData.setMix(AnimationEnum.BREATHES.animationName, "03_hi", 0.2f)
        state = AnimationState(stateData) // Holds the animation state for a skeleton (current animation, time, etc).
        state?.timeScale = 1.0f // Slow all animations down to 50% speed.

        state?.setAnimation(0, "03_hi", false)
        state?.addAnimation(0, "03_hi", true, 10f) // Run after the jump.
    }

    override fun render() {
        state?.update(Gdx.graphics.deltaTime) // Update the animation time.
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        state?.apply(skeleton) // Poses skeleton using current animations. This sets the bones' local SRT.
        skeleton?.updateWorldTransform() // Uses the bones' local SRT to compute their world SRT.

        // Configure the camera, SpriteBatch, and SkeletonRendererDebug.

        camera.update()
        batch.projectionMatrix?.set(camera.combined)
        debugRenderer.shapeRenderer?.projectionMatrix = camera.combined
        batch.begin()
        renderer.draw(batch, skeleton) // Draw the skeleton images.
        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        camera.setToOrtho(false) // Update camera with new size.
    }

    override fun dispose() {

        atlas.dispose()
    }

    fun setAnimate(animate: String?) {
        state?.addAnimation(0, animate, true, 0f)
    }

    fun zoomBig() {
        camera.zoom = 0.5f
    }

    fun zoomSmall() {
        camera.zoom = 1f
    }
}