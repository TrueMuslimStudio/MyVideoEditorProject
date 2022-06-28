//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.myvideoeditorapp.kore.utils;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.struct.TuSdkSizeF;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

public class RectHelper {
    public RectHelper() {
    }

    public static Rect computerCenter(TuSdkSize var0, TuSdkSize var1) {
        if (var1 == null) {
            return null;
        } else {
            RectF var2 = computerCenterRectF(var0, (float)var1.width / (float)var1.height, true);
            return fixedRectF(var0, var2);
        }
    }

    public static Rect computerCenter(TuSdkSize var0, float var1) {
        RectF var2 = computerCenterRectF(var0, var1, true);
        return fixedRectF(var0, var2);
    }

    public static RectF computerCenterRectF(TuSdkSize var0, float var1) {
        return computerCenterRectF(var0, var1, true);
    }

    public static RectF computerCenterRectF(TuSdkSize var0, float var1, boolean var2) {
        if (var0 == null) {
            return null;
        } else {
            RectF var3 = new RectF(0.0F, 0.0F, (float)var0.width, (float)var0.height);
            if (var1 <= 0.0F) {
                return var3;
            } else {
                if (var1 == 1.0F) {
                    var3.right = var3.bottom = (float)Math.min(var0.width, var0.height);
                } else {
                    if (var1 > 1.0F && !var2) {
                        var1 = 1.0F / var1;
                    }

                    float var4 = var0.minMaxRatio();
                    int var5 = var1 < var4 ? 1 : 0;
                    int var6 = var0.width < var0.height ? 1 : 0;
                    float[][] var7 = new float[][]{{(float)var0.width, (float)var0.height / var1}, {(float)var0.height * var1, (float)var0.width}};
                    float[][] var8 = new float[][]{{(float)var0.height, (float)var0.width / var1}, {(float)var0.width * var1, (float)var0.height}};
                    var3.right = var7[var5][1 - var6];
                    var3.bottom = var8[var5][var6];
                }

                var3.left = ((float)var0.width - var3.width()) * 0.5F;
                var3.top = ((float)var0.height - var3.height()) * 0.5F;
                var3.right += var3.left;
                var3.bottom += var3.top;
                return var3;
            }
        }
    }

    public static Rect fixedRectF(TuSdkSize var0, RectF var1) {
        if (var0 != null && var1 != null) {
            Rect var2 = new Rect();
            var2.top = (int)Math.floor((double)var1.top);
            var2.bottom = (int)Math.floor((double)var1.bottom);
            var2.left = (int)Math.floor((double)var1.left);
            var2.right = (int)Math.floor((double)var1.right);
            if (var2.top < 0) {
                var2.bottom -= var2.top;
                var2.top = 0;
            }

            if (var2.left < 0) {
                var2.right -= var2.left;
                var2.left = 0;
            }

            if (var2.height() > var0.height) {
                var2.bottom = var2.top + var0.height;
            }

            if (var2.height() % 2 != 0) {
                --var2.bottom;
            }

            if (var2.width() > var0.width) {
                var2.right = var2.left + var0.width;
            }

            if (var2.width() % 2 != 0) {
                --var2.right;
            }

            return var2;
        } else {
            return null;
        }
    }

    public static RectF fixedCorpPrecentRect(RectF var0, ImageOrientation var1) {
        if (var0 == null) {
            return null;
        } else {
            if (var0.right > 1.0F) {
                var0.right = 1.0F;
            }

            if (var0.bottom > 1.0F) {
                var0.bottom = 1.0F;
            }

            if (var0.left < 0.0F) {
                var0.left = 0.0F;
            }

            if (var0.top < 0.0F) {
                var0.top = 0.0F;
            }

            if (var0.width() > 1.0F) {
                var0.left = 1.0F - var0.right;
            }

            if (var0.height() > 1.0F) {
                var0.top = 1.0F - var0.bottom;
            }

            if (var1 == null) {
                return var0;
            } else {
                RectF var2 = new RectF(var0);
                switch(var1) {
                    case UpMirrored:
                        var2.left = 1.0F - var0.right;
                        var2.right = var2.left + var0.width();
                        break;
                    case Down:
                        var2.left = 1.0F - var0.right;
                        var2.right = var2.left + var0.width();
                        var2.bottom = 1.0F - var0.top;
                        var2.top = var2.bottom - var0.height();
                        break;
                    case DownMirrored:
                        var2.bottom = 1.0F - var0.top;
                        var2.top = var2.bottom - var0.height();
                        break;
                    case Right:
                        var2.left = var0.top;
                        var2.right = var2.left + var0.height();
                        var2.top = 1.0F - var0.right;
                        var2.bottom = var2.top + var0.width();
                        break;
                    case RightMirrored:
                        var2.left = var0.top;
                        var2.right = var2.left + var0.height();
                        var2.top = var0.left;
                        var2.bottom = var2.top + var0.width();
                        break;
                    case Left:
                        var2.left = 1.0F - var0.bottom;
                        var2.right = var2.left + var0.height();
                        var2.top = var0.left;
                        var2.bottom = var2.top + var0.width();
                        break;
                    case LeftMirrored:
                        var2.left = 1.0F - var0.bottom;
                        var2.right = var2.left + var0.height();
                        var2.top = 1.0F - var0.right;
                        var2.bottom = var2.top + var0.width();
                }

                return var2;
            }
        }
    }

    public static TuSdkSize computerOutSize(TuSdkSize var0, float var1, boolean var2) {
        float var3 = var0.getRatioFloat();
        if (var1 == var3) {
            return var0;
        } else {
            TuSdkSize var4 = TuSdkSize.create(var0);
            if (var2) {
                if (var3 > var1) {
                    var4.height = var0.height;
                    var4.width = (int)Math.floor((double)((float)var4.height * var1));
                } else {
                    var4.width = var0.width;
                    var4.height = (int)Math.floor((double)((float)var4.width / var1));
                }
            } else if (var3 > var1) {
                var4.width = var0.width;
                var4.height = (int)Math.floor((double)((float)var4.width / var1));
            } else {
                var4.height = var0.height;
                var4.width = (int)Math.floor((double)((float)var4.height * var1));
            }

            return var4;
        }
    }

    public static Rect computerOutCenter(Rect var0, float var1, boolean var2) {
        Rect var3 = new Rect(var0);
        TuSdkSize var4 = computerOutSize(TuSdkSize.create(var0), var1, var2);
        var3.left -= (var4.width - var3.width()) / 2;
        var3.right = var3.left + var4.width;
        var3.top -= (var4.height - var3.height()) / 2;
        var3.bottom = var3.top + var4.height;
        return var3;
    }

    public static float computerOutScale(Rect var0, float var1, boolean var2) {
        Rect var3 = computerOutCenter(var0, var1, var2);
        float var4 = (float)var3.width() / (float)var0.width();
        float var5 = (float)var3.height() / (float)var0.height();
        return var2 ? Math.min(var4, var5) : Math.max(var4, var5);
    }

    public static float computeAngle(PointF var0, PointF var1) {
        float var2 = getDistanceOfTwoPoints(var0, var1);
        float var3 = (float)Math.asin((double)(Math.abs(var0.y - var1.y) / var2));
        float var4 = (float)((double)(var3 * 180.0F) / 3.141592653589793D);
        if (var1.x - var0.x <= 0.0F && var1.y - var0.y >= 0.0F) {
            var4 = 90.0F - var4;
        } else if (var1.x - var0.x <= 0.0F && var1.y - var0.y <= 0.0F) {
            var4 += 90.0F;
        } else if (var1.x - var0.x >= 0.0F && var1.y - var0.y <= 0.0F) {
            var4 = 270.0F - var4;
        } else if (var1.x - var0.x >= 0.0F && var1.y - var0.y >= 0.0F) {
            var4 += 270.0F;
        }

        var4 -= 235.0F;
        return var4;
    }

    public static float getDistanceOfTwoPoints(PointF var0, PointF var1) {
        return getDistanceOfTwoPoints(var0.x, var0.y, var1.x, var1.y);
    }

    public static float getDistanceOfTwoPoints(float var0, float var1, float var2, float var3) {
        return (float)Math.sqrt(Math.pow((double)(var0 - var2), 2.0D) + Math.pow((double)(var1 - var3), 2.0D));
    }

    public static Rect computerMinMaxSideInRegionRatio(TuSdkSize var0, float var1) {
        if (var0 != null && var0.isSize() && !(var1 <= 0.0F) && !(var1 > 1.0F)) {
            TuSdkSize var2 = TuSdkSize.create(var0);
            if (var0.maxSide() == var0.height) {
                var2.width = (int)((float)var0.height * var1);
            } else {
                var2.height = (int)((float)var0.width * var1);
            }

            var2 = var2.evenSize();
            Rect var3 = makeRectWithAspectRatioInsideRect(var2, new Rect(0, 0, var0.width, var0.height));
            return var3;
        } else {
            return null;
        }
    }

    public static RectF minEnclosingRectangle(PointF var0, TuSdkSize var1, float var2) {
        PointF var3 = new PointF((float)var1.width * 0.5F, (float)var1.height * 0.5F);
        RectF var4 = new RectF();
        PointF var5 = new PointF();
        var5.x = -var3.x;
        var5.y = -var3.y;
        mergeEnclosingRectangle(var4, var5, var2);
        var5.x = var3.x;
        var5.y = -var3.y;
        mergeEnclosingRectangle(var4, var5, var2);
        var5.x = -var3.x;
        var5.y = var3.y;
        mergeEnclosingRectangle(var4, var5, var2);
        var5.x = var3.x;
        var5.y = var3.y;
        mergeEnclosingRectangle(var4, var5, var2);
        var4.left += var0.x;
        var4.top += var0.y;
        var4.right += var0.x;
        var4.bottom += var0.y;
        return var4;
    }

    public static void mergeEnclosingRectangle(RectF var0, PointF var1, float var2) {
        PointF var3 = rotationWithOrigin(var1, var2);
        var0.left = Math.min(var0.left, var3.x);
        var0.right = Math.max(var0.right, var3.x);
        var0.top = Math.min(var0.top, var3.y);
        var0.bottom = Math.max(var0.bottom, var3.y);
    }

    public static PointF rotationWithOrigin(PointF var0, float var1) {
        PointF var2 = new PointF();
        double var3 = (double)var1 * 3.141592653589793D / 180.0D;
        var2.x = (float)((double)var0.x * Math.cos(var3) + (double)var0.y * Math.sin(var3));
        var2.y = (float)((double)var0.y * Math.cos(var3) - (double)var0.x * Math.sin(var3));
        return var2;
    }

    public static Rect rotationWithRotation(Rect var0, TuSdkSize var1, ImageOrientation var2) {
        if (var0 != null && var1 != null && var2 != null && var0.width() > 0 && var0.height() > 0 && var0.right <= var1.width && var0.bottom <= var1.height) {
            Rect var3 = null;
            switch(var2) {
                case UpMirrored:
                    var3 = new Rect(var1.width - var0.right, var0.top, var1.width - var0.left, var0.bottom);
                    break;
                case Down:
                    var3 = new Rect(var1.width - var0.right, var1.height - var0.bottom, var1.width - var0.left, var1.height - var0.top);
                    break;
                case DownMirrored:
                    var3 = new Rect(var0.left, var1.height - var0.bottom, var0.right, var1.height - var0.top);
                    break;
                case Right:
                    var3 = new Rect(var0.top, var1.width - var0.right, var0.bottom, var1.width - var0.left);
                    break;
                case RightMirrored:
                    var3 = new Rect(var0.top, var0.left, var0.bottom, var0.right);
                    break;
                case Left:
                    var3 = new Rect(var1.height - var0.bottom, var0.left, var1.height - var0.top, var0.right);
                    break;
                case LeftMirrored:
                    var3 = new Rect(var1.height - var0.bottom, var1.width - var0.right, var1.height - var0.top, var1.width - var0.left);
                    break;
                case Up:
                default:
                    var3 = new Rect(var0);
            }

            return var3;
        } else {
            return var0;
        }
    }

    public static RectF rotationWithRotation(RectF var0, ImageOrientation var1) {
        if (var0 != null && var1 != null) {
            RectF var2 = null;
            switch(var1) {
                case UpMirrored:
                    var2 = new RectF(1.0F - var0.right, var0.top, 1.0F - var0.left, var0.bottom);
                    break;
                case Down:
                    var2 = new RectF(1.0F - var0.right, 1.0F - var0.bottom, 1.0F - var0.left, 1.0F - var0.top);
                    break;
                case DownMirrored:
                    var2 = new RectF(var0.left, 1.0F - var0.bottom, var0.right, 1.0F - var0.top);
                    break;
                case Right:
                    var2 = new RectF(var0.top, 1.0F - var0.right, var0.bottom, 1.0F - var0.left);
                    break;
                case RightMirrored:
                    var2 = new RectF(var0.top, var0.left, var0.bottom, var0.right);
                    break;
                case Left:
                    var2 = new RectF(1.0F - var0.bottom, var0.left, 1.0F - var0.top, var0.right);
                    break;
                case LeftMirrored:
                    var2 = new RectF(1.0F - var0.bottom, 1.0F - var0.right, 1.0F - var0.top, 1.0F - var0.left);
                    break;
                case Up:
                default:
                    var2 = new RectF(var0);
            }

            return var2;
        } else {
            return var0;
        }
    }

    public static RectF getRectInParent(TuSdkSize var0, TuSdkSize var1, RectF var2) {
        RectF var3 = new RectF(0.0F, 0.0F, (float)var0.width, (float)var0.height);
        if (var1 != null && var2 != null && var0 != null) {
            var3.left = (float)var0.width * var2.left - (float)var1.width * 0.5F;
            var3.top = (float)var0.height * var2.top - (float)var1.height * 0.5F;
            var3.right = var3.left + (float)var0.width * var2.width();
            var3.bottom = var3.top + (float)var0.height * var2.height();
            return var3;
        } else {
            return var3;
        }
    }

    public static RectF getRectInParent(RectF var0, RectF var1) {
        if (var0 == null) {
            return null;
        } else {
            RectF var2 = new RectF(var0.left, var0.top, var0.right, var0.bottom);
            if (var1 == null) {
                return var2;
            } else {
                var2.left += var0.width() * var1.left;
                var2.top += var0.height() * var1.top;
                var2.right = var2.left + var0.width() * var1.width();
                var2.bottom = var2.top + var0.height() * var1.height();
                return var2;
            }
        }
    }

    public static Rect makeRectWithAspectRatioInsideRect(TuSdkSize var0, Rect var1) {
        if (var0 != null && var1 != null) {
            TuSdkSize var2 = new TuSdkSize();
            var2.width = var1.width();
            var2.height = (int)Math.floor((double)((float)var2.width / var0.getRatioFloat()));
            if (var2.height > var1.height()) {
                var2.height = var1.height();
                var2.width = (int)Math.floor((double)((float)var2.height * var0.getRatioFloat()));
            }

            Rect var3 = new Rect(var1);
            var3.left = var1.left + (var1.width() - var2.width) / 2;
            var3.right = var3.left + var2.width;
            var3.top = var1.top + (var1.height() - var2.height) / 2;
            var3.bottom = var3.top + var2.height;
            return var3;
        } else {
            return null;
        }
    }

    public static RectF makeRectWithAspectRatioOutsideRect(TuSdkSize var0, RectF var1) {
        if (var0 != null && var1 != null) {
            TuSdkSizeF var2 = new TuSdkSizeF();
            var2.width = var1.width();
            var2.height = (float)((int)Math.floor((double)(var2.width / var0.getRatioFloat())));
            if (var2.height < var1.height()) {
                var2.height = var1.height();
                var2.width = (float)((int)Math.floor((double)(var2.height * var0.getRatioFloat())));
            }

            RectF var3 = new RectF(var1);
            var3.left = (var2.width - var1.width()) / 2.0F - var1.left;
            var3.right = var3.left + var2.width;
            var3.top = (var2.height - var1.height()) / 2.0F - var1.top;
            var3.bottom = var3.top + var2.height;
            return var3;
        } else {
            return null;
        }
    }

    public static double computerPotintDistance(Point var0, Point var1) {
        if (var0 != null && var1 != null) {
            float var2 = (float)(var0.x - var1.x);
            float var3 = (float)(var0.y - var1.y);
            return Math.sqrt((double)(var2 * var2 + var3 * var3));
        } else {
            return 0.0D;
        }
    }

    public static double computerPotintDistance(PointF var0, PointF var1) {
        if (var0 != null && var1 != null) {
            float var2 = var0.x - var1.x;
            float var3 = var0.y - var1.y;
            return Math.sqrt((double)(var2 * var2 + var3 * var3));
        } else {
            return 0.0D;
        }
    }

    public static float[] textureCoordinates(ImageOrientation var0, RectF var1) {
        if (var1 != null && var0 != null) {
            var1 = rotationWithRotation(var1, var0);
            float[] var2 = new float[8];
            var2[0] = var1.left;
            var2[1] = var1.top;
            var2[2] = var1.right;
            var2[3] = var2[1];
            var2[4] = var2[0];
            var2[5] = var1.bottom;
            var2[6] = var2[2];
            var2[7] = var2[5];
            return a(var0, var2);
        } else {
            return new float[]{0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F};
        }
    }

    public static float[] displayCoordinates(ImageOrientation var0, RectF var1) {
        if (var1 != null && var0 != null) {
            var1 = rotationWithRotation(var1, var0);
            float[] var2 = new float[8];
            var2[0] = var1.left;
            var2[1] = var1.bottom;
            var2[2] = var1.right;
            var2[3] = var2[1];
            var2[4] = var2[0];
            var2[5] = var1.top;
            var2[6] = var2[2];
            var2[7] = var2[5];
            return a(var0, var2);
        } else {
            return new float[]{0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F};
        }
    }

    public static float[] textureVertices(ImageOrientation var0, RectF var1) {
        if (var1 != null && var0 != null) {
            var1 = rotationWithRotation(var1, var0);
            float[] var2 = new float[8];
            var2[0] = var1.left * 2.0F - 1.0F;
            var2[1] = var1.top * 2.0F - 1.0F;
            var2[2] = var1.right * 2.0F - 1.0F;
            var2[3] = var2[1];
            var2[4] = var2[0];
            var2[5] = var1.bottom * 2.0F - 1.0F;
            var2[6] = var2[2];
            var2[7] = var2[5];
            return var2;
        } else {
            return new float[]{-1.0F, -1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F};
        }
    }

    public static float[] displayVertices(ImageOrientation var0, RectF var1) {
        if (var1 != null && var0 != null) {
            var1 = rotationWithRotation(var1, var0);
            float[] var2 = new float[8];
            var2[0] = var1.left * 2.0F - 1.0F;
            var2[1] = 1.0F - var1.bottom * 2.0F;
            var2[2] = var1.right * 2.0F - 1.0F;
            var2[3] = var2[1];
            var2[4] = var2[0];
            var2[5] = 1.0F - var1.top * 2.0F;
            var2[6] = var2[2];
            var2[7] = var2[5];
            return var2;
        } else {
            return new float[]{-1.0F, -1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F};
        }
    }

    private static float[] a(ImageOrientation var0, float[] var1) {
        if (var0 != null && var1 != null) {
            switch(var0) {
                case UpMirrored:
                    return new float[]{var1[2], var1[3], var1[0], var1[1], var1[6], var1[7], var1[4], var1[5]};
                case Down:
                    return new float[]{var1[6], var1[7], var1[4], var1[5], var1[2], var1[3], var1[0], var1[1]};
                case DownMirrored:
                    return new float[]{var1[4], var1[5], var1[6], var1[7], var1[0], var1[1], var1[2], var1[3]};
                case Right:
                    return new float[]{var1[4], var1[5], var1[0], var1[1], var1[6], var1[7], var1[2], var1[3]};
                case RightMirrored:
                    return new float[]{var1[0], var1[1], var1[4], var1[5], var1[2], var1[3], var1[6], var1[7]};
                case Left:
                    return new float[]{var1[2], var1[3], var1[6], var1[7], var1[0], var1[1], var1[4], var1[5]};
                case LeftMirrored:
                    return new float[]{var1[6], var1[7], var1[2], var1[3], var1[4], var1[5], var1[0], var1[1]};
                case Up:
                default:
                    return var1;
            }
        } else {
            return var1;
        }
    }
}
