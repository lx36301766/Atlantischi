package pl.atlantischi.robusttest.patch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.meituan.robust.Patch;
import com.meituan.robust.PatchManipulate;

import android.content.Context;
import android.os.Environment;

/**
 * Created on 25/07/2017.
 *
 * @author lx
 */

public class PatchManipulateImpl extends PatchManipulate {

    @Override
    protected List<Patch> fetchPatchList(Context context) {
        Patch patch = new Patch();
        patch.setName("test patch");
        patch.setLocalPath(Environment.getExternalStorageDirectory().getPath()
                + File.separator + "robust"
                + File.separator + "patch");
        patch.setPatchesInfoImplClassFullName(PatchManipulateImpl.class.getName());
        List<Patch> patches = new ArrayList<>();
        patches.add(patch);
        return patches;
    }

    public void copy(String srcPath, String dstPath) throws IOException {
        File src = new File(srcPath);
        if (!src.exists()) {
            throw new RuntimeException("source patch does not exist ");
        }
        File dst = new File(dstPath);
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    @Override
    protected boolean verifyPatch(Context context, Patch patch) {
        patch.setTempPath(context.getCacheDir() + File.separator + "robust" + File.separator + "patch");
        try {
            copy(patch.getLocalPath(), patch.getTempPath());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "copy source patch to local patch error, no patch execute in path " + patch.getTempPath());
        }

        return true;
    }

    @Override
    protected boolean ensurePatchExist(Patch patch) {
        return true;
    }

}
