/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package aviation.recoding.zeppelin.vision.ocr;

import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import aviation.recoding.zeppelin.ui.scanning.ScanFragment;
import aviation.recoding.zeppelin.vision.camera.GraphicOverlay;


public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    private ScanFragment mScanFragment;
    private GraphicOverlay mGraphicOverlay;

    public OcrDetectorProcessor(ScanFragment scanFragment, GraphicOverlay graphicOverlay) {
        this.mScanFragment = scanFragment;
        mGraphicOverlay = graphicOverlay;
    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mGraphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
            mGraphicOverlay.add(graphic);
            mScanFragment.addTextBlock(item);
        }
    }

    // Process text blocks



    /**
     * Frees the resources associated with this detection processor.
     */
    @Override
    public void release() {
        mGraphicOverlay.clear();
    }
}
