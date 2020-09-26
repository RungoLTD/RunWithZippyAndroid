package com.rungo.runwithzippy.utils.animationModel

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.esotericsoftware.spine.Animation
import com.esotericsoftware.spine.SkeletonData
import com.esotericsoftware.spine.view.core.DisplayRenderParam
import com.esotericsoftware.spine.view.core.SkeletonDisplayer
import com.esotericsoftware.spine.view.core.SkeletonDisplayerBuilder
import com.rungo.runwithzippy.utils.AnimationEnum

class Zippy2 : ApplicationAdapter() {

    var renderParam: DisplayRenderParam? = null
    private var skeletonDisplayer: SkeletonDisplayer? = null

    override fun create() {
        super.create()
        skeletonDisplayer =
            SkeletonDisplayerBuilder()
                .setAtlasGdxFilePath("coma/coma.atlas")
                .setSkeletonGdxFilePath("coma/coma.json")
                .build()

        skeletonDisplayer?.create()

        skeletonDisplayer?.setAnimation(AnimationEnum.BREATHES.animationName, true, 0.37f, Animation.MixBlend.replace, 1.0f)
    }

    override fun render() {
        super.render()
        skeletonDisplayer?.state?.update(Gdx.graphics.deltaTime)
        skeletonDisplayer?.skeleton?.updateWorldTransform()

        skeletonDisplayer?.setRenderParam(renderParam)
        skeletonDisplayer?.render()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        skeletonDisplayer?.resize(width, height)
    }

    override fun resume() {
        super.resume()
    }
}
