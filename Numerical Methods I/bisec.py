# -*- coding: utf-8 -*-
def bisec(f,a,b,N):
    an=a
    bn=b
    fan=f(an)
    fbn=f(bn)
    if fan==0:
        print(str(a)+'es raíz de la función')
        return a
    elif fbn==0:
        print(str(b)+'es raíz de la función')
        return b
    elif fan*fbn>0:
        print('No hay cambio de signo: no se puede aplicar el método')
        #return
    for k in range(N):
        cn=(an+bn)/2.
        fcn=f(cn)
        if fcn==0:
            print(str(cn)+'es raíz de la función')
            return cn
        elif fan*fcn<0:
            bn=cn
            fbn=fcn
        else:
            an=cn
            fan=fcn
    print('La aproximación de la raíz tras '+str(N)+' iteraciones es '+str(cn))
    return cn