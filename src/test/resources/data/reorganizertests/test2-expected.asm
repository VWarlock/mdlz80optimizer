; Test to make sure moves do not happen if they will break jrs
	ld a, 1
	jp label1
___expanded_macro___1.0:
    nop
___expanded_macro___1.1:
    nop
___expanded_macro___1.2:
    nop
___expanded_macro___1.3:
    nop
___expanded_macro___1.4:
    nop
___expanded_macro___1.5:
    nop
___expanded_macro___1.6:
    nop
___expanded_macro___1.7:
    nop
___expanded_macro___1.8:
    nop
___expanded_macro___1.9:
    nop
___expanded_macro___1.10:
    nop
___expanded_macro___1.11:
    nop
___expanded_macro___1.12:
    nop
___expanded_macro___1.13:
    nop
___expanded_macro___1.14:
    nop
___expanded_macro___1.15:
    nop
___expanded_macro___1.16:
    nop
___expanded_macro___1.17:
    nop
___expanded_macro___1.18:
    nop
___expanded_macro___1.19:
    nop
___expanded_macro___1.20:
    nop
___expanded_macro___1.21:
    nop
___expanded_macro___1.22:
    nop
___expanded_macro___1.23:
    nop
___expanded_macro___1.24:
    nop
___expanded_macro___1.25:
    nop
___expanded_macro___1.26:
    nop
___expanded_macro___1.27:
    nop
___expanded_macro___1.28:
    nop
___expanded_macro___1.29:
    nop
___expanded_macro___1.30:
    nop
___expanded_macro___1.31:
    nop
___expanded_macro___1.32:
    nop
___expanded_macro___1.33:
    nop
___expanded_macro___1.34:
    nop
___expanded_macro___1.35:
    nop
___expanded_macro___1.36:
    nop
___expanded_macro___1.37:
    nop
___expanded_macro___1.38:
    nop
___expanded_macro___1.39:
    nop
___expanded_macro___1.40:
    nop
___expanded_macro___1.41:
    nop
___expanded_macro___1.42:
    nop
___expanded_macro___1.43:
    nop
___expanded_macro___1.44:
    nop
___expanded_macro___1.45:
    nop
___expanded_macro___1.46:
    nop
___expanded_macro___1.47:
    nop
___expanded_macro___1.48:
    nop
___expanded_macro___1.49:
    nop
___expanded_macro___1.50:
    nop
___expanded_macro___1.51:
    nop
___expanded_macro___1.52:
    nop
___expanded_macro___1.53:
    nop
___expanded_macro___1.54:
    nop
___expanded_macro___1.55:
    nop
___expanded_macro___1.56:
    nop
___expanded_macro___1.57:
    nop
___expanded_macro___1.58:
    nop
___expanded_macro___1.59:
    nop
___expanded_macro___1.60:
    nop
___expanded_macro___1.61:
    nop
___expanded_macro___1.62:
    nop
___expanded_macro___1.63:
    nop
___expanded_macro___1.64:
    nop
___expanded_macro___1.65:
    nop
___expanded_macro___1.66:
    nop
___expanded_macro___1.67:
    nop
___expanded_macro___1.68:
    nop
___expanded_macro___1.69:
    nop
___expanded_macro___1.70:
    nop
___expanded_macro___1.71:
    nop
___expanded_macro___1.72:
    nop
___expanded_macro___1.73:
    nop
___expanded_macro___1.74:
    nop
___expanded_macro___1.75:
    nop
___expanded_macro___1.76:
    nop
___expanded_macro___1.77:
    nop
___expanded_macro___1.78:
    nop
___expanded_macro___1.79:
    nop
___expanded_macro___1.80:
    nop
___expanded_macro___1.81:
    nop
___expanded_macro___1.82:
    nop
___expanded_macro___1.83:
    nop
___expanded_macro___1.84:
    nop
___expanded_macro___1.85:
    nop
___expanded_macro___1.86:
    nop
___expanded_macro___1.87:
    nop
___expanded_macro___1.88:
    nop
___expanded_macro___1.89:
    nop
___expanded_macro___1.90:
    nop
___expanded_macro___1.91:
    nop
___expanded_macro___1.92:
    nop
___expanded_macro___1.93:
    nop
___expanded_macro___1.94:
    nop
___expanded_macro___1.95:
    nop
___expanded_macro___1.96:
    nop
___expanded_macro___1.97:
    nop
___expanded_macro___1.98:
    nop
___expanded_macro___1.99:
    nop
___expanded_macro___1.100:
    nop
___expanded_macro___1.101:
    nop
___expanded_macro___1.102:
    nop
___expanded_macro___1.103:
    nop
___expanded_macro___1.104:
    nop
___expanded_macro___1.105:
    nop
___expanded_macro___1.106:
    nop
___expanded_macro___1.107:
    nop
___expanded_macro___1.108:
    nop
___expanded_macro___1.109:
    nop
___expanded_macro___1.110:
    nop
___expanded_macro___1.111:
    nop
___expanded_macro___1.112:
    nop
___expanded_macro___1.113:
    nop
___expanded_macro___1.114:
    nop
___expanded_macro___1.115:
    nop
___expanded_macro___1.116:
    nop
___expanded_macro___1.117:
    nop
___expanded_macro___1.118:
    nop
___expanded_macro___1.119:
    nop
___expanded_macro___1.120:
    nop
___expanded_macro___1.121:
    nop
___expanded_macro___1.122:
    nop
___expanded_macro___1.123:
    nop
___expanded_macro___1.124:
    nop
___expanded_macro___1.125:
    nop
___expanded_macro___1.126:
    nop
___expanded_macro___1.127:
    nop
label1:
	add a, b
	jr nc, label3
; 	jp label2  ; -mdl
label2:
	jr label2
label3:
	jr label3
